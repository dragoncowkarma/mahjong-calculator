package com.dragoncowkarma.mahcalc.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.dragoncowkarma.mahcalc.calculator.MahjongCalculator
import com.dragoncowkarma.mahcalc.calculator.ScoreCalculator
import com.dragoncowkarma.mahcalc.models.AgariEvaluator
import com.dragoncowkarma.mahcalc.models.MahjongTile
import com.dragoncowkarma.mahcalc.models.MatchContext
import com.dragoncowkarma.mahcalc.models.MockDataGenerator
import com.dragoncowkarma.mahcalc.models.ScoreResult
import com.dragoncowkarma.mahcalc.models.SpatialTileSorter
import com.dragoncowkarma.mahcalc.models.nonMaximumSuppression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import me.tatarka.inject.annotations.Inject
import com.dragoncowkarma.mahcalc.models.TileDetectionModel

@Inject
class MahjongScreenModel(
    private val tileDetectionModel: TileDetectionModel
) : ScreenModel {
    private val _isCalculating = MutableStateFlow(false)
    val isCalculating: StateFlow<Boolean> = _isCalculating.asStateFlow()

    private val _matchContext = MutableStateFlow(MatchContext())
    val matchContext: StateFlow<MatchContext> = _matchContext.asStateFlow()

    private val _detectedTiles = MutableStateFlow<List<MahjongTile>>(emptyList())
    val detectedTiles: StateFlow<List<MahjongTile>> = _detectedTiles.asStateFlow()

    private val _resultState = MutableStateFlow<ScoreResult?>(null)
    val resultState: StateFlow<ScoreResult?> = _resultState.asStateFlow()

    fun updateMatchContext(newContext: MatchContext) {
        _matchContext.value = newContext
        // If we already have detected tiles, recalculate score
        val currentTiles = _detectedTiles.value
        if (currentTiles.size == 14) {
            calculateScore(currentTiles.map { it.id }.toIntArray())
        }
    }

    fun updateTiles(newTiles: List<MahjongTile>) {
        _detectedTiles.value = newTiles
        if (newTiles.size == 14) {
            calculateScore(newTiles.map { it.id }.toIntArray())
        } else {
            _resultState.value = null
        }
    }

    fun processCameraFrame(frameData: ByteArray, width: Int, height: Int) {
        if (_isCalculating.value) return

        screenModelScope.launch {
            _isCalculating.value = true
            _resultState.value = null

            // Offload heavy processing to Default dispatcher
            val result = withContext(Dispatchers.Default) {
                try {
                    // 1. Use mock bounding boxes (real detection model would be platform-specific)
                    val rawBoxes = tileDetectionModel.detect(frameData, width, height)

                    // 2. Filter boxes with Non-Maximum Suppression (NMS)
                    val filteredBoxes = nonMaximumSuppression(rawBoxes, iouThreshold = 0.5f)

                    // 3. Run SpatialTileSorter
                    val tileIds = SpatialTileSorter.sortToTileIds(filteredBoxes)

                    tileIds
                } catch (e: Exception) {
                    IntArray(0)
                }
            }

            _detectedTiles.value = result.map { MahjongTile(it) }

            if (result.size == 14) {
                calculateScore(result)
            }

            _isCalculating.value = false
        }
    }

    private fun calculateScore(hand: IntArray) {
        if (hand.size != 14) {
            _resultState.value = null
            return
        }

        val context = _matchContext.value
        // We'll treat the last tile as the winning tile for simplicity, or we could require user input.
        val winningTile = hand.last()

        val isAgari = AgariEvaluator.isAgari(hand)
        if (isAgari) {
            val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile, context)

            val han = calculateHanFromYakuNames(yaku)

            val scoreResult = ScoreCalculator.calculateScore(han, fu, context, yaku)
            _resultState.value = scoreResult
        } else {
            _resultState.value = null
        }
    }

    private fun calculateHanFromYakuNames(yakuList: List<String>): Int {
        // A simple approximation. In a real scenario, the Calculator should return the total Han as well.
        var han = 0
        val twoHanYaku = setOf("Toitoi", "Sanankou", "Sankantsu", "Shousangen", "Honrouto", "Chanta", "Ittsu", "Sanshoku Doujun", "Sanshoku Doukou")
        val threeHanYaku = setOf("Honitsu", "Junchan", "Ryanpeikou")
        val sixHanYaku = setOf("Chinitsu")
        val yakuman = setOf("Kokushi Musou", "Suuankou", "Daisangen", "Shousuushii", "Daisuushii", "Tsuuiisou", "Chinrouto", "Ryuuiisou", "Kyuuren Poutou", "Sukantsu", "Tenhou", "Chiihou")

        for (yaku in yakuList) {
            if (yakuman.contains(yaku)) return 13
            han += when (yaku) {
                in twoHanYaku -> 2
                in threeHanYaku -> 3
                in sixHanYaku -> 6
                else -> 1 // Default to 1 han for things like Riichi, Tsumo, Pinfu, Tanyao, etc.
            }
        }

        return han + _matchContext.value.doraCount
    }
}
