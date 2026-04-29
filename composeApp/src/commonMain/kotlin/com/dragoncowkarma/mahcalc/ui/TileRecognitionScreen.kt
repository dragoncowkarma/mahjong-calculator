package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dragoncowkarma.mahcalc.AppComponent
import com.dragoncowkarma.mahcalc.models.ImageSource
import com.dragoncowkarma.mahcalc.LocalAppComponent

class TileRecognitionScreen : Screen {

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

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .safeContentPadding()
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Tile Recognition", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { launchPicker() }) {
                        Text("Import Image")
                    }
                    Button(onClick = { navigator.pop() }) {
                        Text("Back")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                when (val currentState = state) {
                    is TileRecognitionState.Idle -> {
                        Text("Select an image to analyze.", style = MaterialTheme.typography.bodyLarge)
                    }
                    is TileRecognitionState.Loading -> {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Analyzing...", style = MaterialTheme.typography.bodyLarge)
                    }
                    is TileRecognitionState.Error -> {
                        Text("Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
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
                        Image(
                            bitmap = currentState.image,
                            contentDescription = "Preview",
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                            Text("Hand: ${currentState.handTiles}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))

                            if (currentState.melds.isNotEmpty()) {
                                Text("Melds: ${currentState.melds}", style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Text("Discarded: ${currentState.discardedTiles}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
