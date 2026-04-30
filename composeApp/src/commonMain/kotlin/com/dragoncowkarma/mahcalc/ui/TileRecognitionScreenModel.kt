package com.dragoncowkarma.mahcalc.ui

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.dragoncowkarma.mahcalc.models.BoundingBox
import com.dragoncowkarma.mahcalc.models.ImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import com.dragoncowkarma.mahcalc.models.nonMaximumSuppression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

sealed class TileRecognitionState {
    object Idle : TileRecognitionState()
    object Loading : TileRecognitionState()
    data class ImagePreview(val image: ImageBitmap) : TileRecognitionState()
    data class ResultList(
        val handTiles: List<Int>,
        val discardedTiles: List<Int>,
        val melds: List<List<Int>>,
        val image: ImageBitmap
    ) : TileRecognitionState()
    data class Error(val message: String) : TileRecognitionState()
}

@Inject
class TileRecognitionScreenModel(
    private val tileDetectionModel: TileDetectionModel,
    private val imageBitmapDecoder: ImageBitmapDecoder
) : ScreenModel {

    private val _state = MutableStateFlow<TileRecognitionState>(TileRecognitionState.Idle)
    val state: StateFlow<TileRecognitionState> = _state.asStateFlow()

    fun processImage(imageBytes: ByteArray?) {
        if (imageBytes == null || imageBytes.isEmpty()) {
            _state.value = TileRecognitionState.Error("Invalid or empty image selected.")
            return
        }

        screenModelScope.launch {
            _state.value = TileRecognitionState.Loading

            try {
                val bitmap = withContext(Dispatchers.Default) {
                    imageBitmapDecoder.decode(imageBytes)
                }

                if (bitmap == null) {
                    _state.value = TileRecognitionState.Error("Failed to decode image.")
                    return@launch
                }

                // Show preview while processing
                _state.value = TileRecognitionState.ImagePreview(bitmap)

                // Offload to background thread for detection
                withContext(Dispatchers.Default) {
                    val detectionResult = runCatching {
                        tileDetectionModel.detect(imageBytes, bitmap.width, bitmap.height)
                    }

                    if (detectionResult.isFailure) {
                        _state.value = TileRecognitionState.Error(
                            detectionResult.exceptionOrNull()?.message ?: "Detection engine error"
                        )
                        return@withContext
                    }

                    val rawBoxes = detectionResult.getOrDefault(emptyList())
                    val filteredBoxes = nonMaximumSuppression(rawBoxes, iouThreshold = 0.5f)

                    // Classification heuristic
                    if (filteredBoxes.isEmpty()) {
                        _state.value = TileRecognitionState.ResultList(
                            emptyList(), emptyList(), emptyList(), bitmap
                        )
                        return@withContext
                    }

                    val avgHeight = filteredBoxes.sumOf { it.height.toDouble() } / filteredBoxes.size
                    val thresholdY = filteredBoxes.map { it.y }.minOrNull()?.plus(avgHeight * 2) ?: 0.0

                    // Split into Hand (bottom) and Discard (top) based on arbitrary threshold
                    // Hand usually has a Y value significantly larger than disards in standard camera angles
                    val handBoxes = filteredBoxes.filter { it.y >= thresholdY }
                    val discardBoxes = filteredBoxes.filter { it.y < thresholdY }

                    val sortedDiscards = discardBoxes.sortedBy { it.x }.map { it.classId }

                    // Process Hand to find Melds
                    val sortedHandBoxes = handBoxes.sortedBy { it.x }

                    val handTiles = mutableListOf<Int>()
                    val melds = mutableListOf<List<Int>>()

                    if (sortedHandBoxes.isNotEmpty()) {
                        // Delta X logic for melds (melds are separated by a larger gap usually)
                        val deltaXThreshold = avgHeight * 1.2

                        var currentGroup = mutableListOf(sortedHandBoxes.first())
                        val groups = mutableListOf(currentGroup)

                        for (i in 1 until sortedHandBoxes.size) {
                            val currentBox = sortedHandBoxes[i]
                            val prevBox = sortedHandBoxes[i - 1]

                            val gap = currentBox.x - (prevBox.x + prevBox.width)
                            if (gap > deltaXThreshold) {
                                currentGroup = mutableListOf(currentBox)
                                groups.add(currentGroup)
                            } else {
                                currentGroup.add(currentBox)
                            }
                        }

                        // Usually the main hand is the largest group, and melds are smaller groups (3-4 tiles) on the right
                        // Assuming left to right: Hand, then Melds
                        if (groups.isNotEmpty()) {
                            handTiles.addAll(groups.first().map { it.classId })
                            for (i in 1 until groups.size) {
                                melds.add(groups[i].map { it.classId })
                            }
                        }
                    }

                    _state.value = TileRecognitionState.ResultList(
                        handTiles = handTiles,
                        discardedTiles = sortedDiscards,
                        melds = melds,
                        image = bitmap
                    )
                }

            } catch (e: Exception) {
                _state.value = TileRecognitionState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
