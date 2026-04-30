package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dragoncowkarma.mahcalc.LocalAppComponent
import com.dragoncowkarma.mahcalc.models.MahjongTile

class YakuCalculationScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val component = LocalAppComponent.current
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { component.yakuCalculationScreenModel }

        val selectedTiles by screenModel.selectedTiles.collectAsState()
        val resultState by screenModel.resultState.collectAsState()
        val validationError by screenModel.validationError.collectAsState()

        val allTiles = (0..33).map { MahjongTile(it) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("역 판별") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Text("< 뒤로가기")
                        }
                    },
                    actions = {
                        TextButton(onClick = { screenModel.clearTiles() }) {
                            Text("초기화")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Currently selected tiles
                Text("선택된 패 (${selectedTiles.size}/18):", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                // Display selected tiles. Click to remove.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (i in selectedTiles.indices) {
                        val tile = selectedTiles[i]
                        Box(
                            modifier = Modifier
                                .clickable { screenModel.removeTile(i) }
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = getMahjongTileString(tile.id),
                                fontSize = 32.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Validation Error
                if (validationError != null) {
                    Text(
                        text = validationError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Calculate Button
                Button(
                    onClick = { screenModel.calculate() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedTiles.size in 14..18
                ) {
                    Text("역 계산하기")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Result display
                if (resultState != null) {
                    ScoreResultDashboard(scoreResult = resultState!!)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tile palette
                Text("패 추가 (탭하세요):", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(allTiles) { tile ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
                                .clickable { screenModel.addTile(tile) }
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = getMahjongTileString(tile.id),
                                fontSize = 32.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
