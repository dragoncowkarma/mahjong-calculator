package com.dragoncowkarma.mahcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dragoncowkarma.mahcalc.ui.ContextModal
import com.dragoncowkarma.mahcalc.ui.MahjongScreenModel
import com.dragoncowkarma.mahcalc.ui.ScoreResultDashboard
import com.dragoncowkarma.mahcalc.ui.TileCorrectionPanel
import com.dragoncowkarma.mahcalc.ui.YakuListScreen

/**
 * The main Voyager Screen for the Mahjong Calculator.
 */
class MahjongScreen : Screen {

    @Composable
    override fun Content() {
        MaterialTheme {
            val component = LocalAppComponent.current
            val navigator = LocalNavigator.currentOrThrow
            val screenModel = rememberScreenModel { component.mahjongScreenModel }

            val isCalculating by screenModel.isCalculating.collectAsState()
            val matchContext by screenModel.matchContext.collectAsState()
            val detectedTiles by screenModel.detectedTiles.collectAsState()
            val resultState by screenModel.resultState.collectAsState()

            var showContextModal by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Mock "Simulate Detection" button since we don't have a real camera feed setup yet
                Row {
                    Button(onClick = {
                        // Pass dummy byte array to simulate detection
                        screenModel.processCameraFrame(ByteArray(0), 1080, 1920)
                    }, enabled = !isCalculating) {
                        Text("Simulate Detection")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = { showContextModal = true }) {
                        Text("Game State")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(onClick = { navigator.push(YakuListScreen()) }) {
                        Text("역 목록")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = { navigator.push(com.dragoncowkarma.mahcalc.ui.TileRecognitionScreen()) }) {
                        Text("Tile Recognition")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isCalculating) {
                    CircularProgressIndicator()
                }

                // Detected Tiles Panel
                if (detectedTiles.isNotEmpty()) {
                    TileCorrectionPanel(
                        tiles = detectedTiles,
                        onTilesCorrected = { correctedTiles ->
                            screenModel.updateTiles(correctedTiles)
                        }
                    )
                } else if (!isCalculating) {
                    Text("No tiles detected. Simulate detection to begin.", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Score Result Dashboard
                resultState?.let { score ->
                    ScoreResultDashboard(scoreResult = score)
                }
            }

            if (showContextModal) {
                ContextModal(
                    context = matchContext,
                    onApply = { newContext ->
                        screenModel.updateMatchContext(newContext)
                    },
                    onDismissRequest = { showContextModal = false }
                )
            }
        }
    }
}
