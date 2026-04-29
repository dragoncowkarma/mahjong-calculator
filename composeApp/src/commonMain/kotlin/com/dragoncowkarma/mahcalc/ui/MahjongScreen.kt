package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.model.rememberScreenModel
import com.dragoncowkarma.mahcalc.AppComponent

class MahjongScreen(private val component: AppComponent) : Screen {

    @Composable
    override fun Content() {
        MaterialTheme {
            val screenModel: MahjongScreenModel = rememberScreenModel { component.mahjongScreenModel }

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
