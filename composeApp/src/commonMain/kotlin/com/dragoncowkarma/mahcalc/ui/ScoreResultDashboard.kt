package com.dragoncowkarma.mahcalc.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dragoncowkarma.mahcalc.models.ScoreResult

fun getLimitName(han: Int, fu: Int): String? {
    if (han >= 13) return "Yakuman"
    if (han >= 11) return "Sanbaiman"
    if (han >= 8) return "Baiman"
    if (han >= 6) return "Haneman"
    if (han >= 5 || (han == 4 && fu >= 40) || (han == 3 && fu >= 70)) return "Mangan"
    return null
}

@Composable
fun ScoreResultDashboard(scoreResult: ScoreResult, modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }
    var targetPoints by remember { mutableStateOf(0) }

    LaunchedEffect(scoreResult) {
        isVisible = true
        targetPoints = scoreResult.pointsToReceive
    }

    val animatedPoints by animateIntAsState(
        targetValue = targetPoints,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "PointsAnimation"
    )

    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Score Result",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Han: ${scoreResult.totalHan} | Fu: ${scoreResult.totalFu}", style = MaterialTheme.typography.titleMedium)

            val limitName = getLimitName(scoreResult.totalHan, scoreResult.totalFu)
            AnimatedVisibility(
                visible = isVisible && limitName != null,
                enter = fadeIn() + expandVertically()
            ) {
                if (limitName != null) {
                    Text(
                        text = limitName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Points: $animatedPoints", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)

            Spacer(modifier = Modifier.height(16.dp))

            if (scoreResult.yakuList.isNotEmpty()) {
                Text(text = "Yaku:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
                    items(scoreResult.yakuList) { yaku ->
                        Text(text = "• $yaku", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewScoreResultDashboard() {
    MaterialTheme {
        ScoreResultDashboard(
            scoreResult = ScoreResult(
                totalHan = 5,
                totalFu = 30,
                yakuList = listOf("Riichi", "Ippatsu", "Tsumo", "Pinfu", "Dora 1"),
                pointsToReceive = 8000
            )
        )
    }
}
