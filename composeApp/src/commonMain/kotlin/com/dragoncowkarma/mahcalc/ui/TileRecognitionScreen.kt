package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dragoncowkarma.mahcalc.AppComponent
import com.dragoncowkarma.mahcalc.models.ImageSource
import com.dragoncowkarma.mahcalc.models.MahjongTile
import com.dragoncowkarma.mahcalc.models.toKoreanDisplayString
import com.dragoncowkarma.mahcalc.LocalAppComponent

class TileRecognitionScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        MaterialTheme {
            val component = LocalAppComponent.current
            val navigator = LocalNavigator.currentOrThrow
            val screenModel = rememberScreenModel { component.tileRecognitionScreenModel }
            val state by screenModel.state.collectAsState()

            val imagePicker = component.imagePicker

            val launchPicker = imagePicker.registerPicker { bytes ->
                screenModel.processImage(bytes)
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("패 인식") },
                        navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {
                                Text("< 뒤로가기")
                            }
                        },
                        actions = {
                            TextButton(onClick = { launchPicker() }) {
                                Text("이미지 불러오기")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when (val currentState = state) {
                    is TileRecognitionState.Idle -> {
                        Text("분석할 이미지를 선택하세요.", style = MaterialTheme.typography.bodyLarge)
                    }
                    is TileRecognitionState.Loading -> {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("분석 중...", style = MaterialTheme.typography.bodyLarge)
                    }
                    is TileRecognitionState.Error -> {
                        Text("오류: ${currentState.message}", color = MaterialTheme.colorScheme.error)
                    }
                    is TileRecognitionState.ImagePreview -> {
                        Image(
                            bitmap = currentState.image,
                            contentDescription = "Preview",
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CircularProgressIndicator()
                    }
                    is TileRecognitionState.ResultList -> {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                            Image(
                                bitmap = currentState.image,
                                contentDescription = "Preview",
                                modifier = Modifier.fillMaxSize()
                            )
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height
                                val imageWidth = currentState.image.width.toFloat()
                                val imageHeight = currentState.image.height.toFloat()

                                val scaleX = canvasWidth / imageWidth
                                val scaleY = canvasHeight / imageHeight
                                val scale = minOf(scaleX, scaleY)

                                val drawWidth = imageWidth * scale
                                val drawHeight = imageHeight * scale
                                val offsetX = (canvasWidth - drawWidth) / 2f
                                val offsetY = (canvasHeight - drawHeight) / 2f

                                currentState.boundingBoxes.forEach { box ->
                                    val mappedX = offsetX + (box.x * scale)
                                    val mappedY = offsetY + (box.y * scale)
                                    val mappedWidth = box.width * scale
                                    val mappedHeight = box.height * scale

                                    drawRect(
                                        color = Color.Red,
                                        topLeft = Offset(mappedX, mappedY),
                                        size = Size(mappedWidth, mappedHeight),
                                        style = Stroke(width = 3.dp.toPx())
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                            val handDisplay = currentState.handTiles.map { MahjongTile(it) }.toKoreanDisplayString()
                            Text("손패: $handDisplay", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))

                            if (currentState.melds.isNotEmpty()) {
                                val meldsDisplay = currentState.melds.joinToString(" | ") { meld ->
                                    meld.map { MahjongTile(it) }.toKoreanDisplayString()
                                }
                                Text("울음패(부로): $meldsDisplay", style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            val discardedDisplay = currentState.discardedTiles.map { MahjongTile(it) }.toKoreanDisplayString()
                            Text("버림패: $discardedDisplay", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
}
