package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dragoncowkarma.mahcalc.models.MatchContext

@Composable
fun GameStatePanel(
    context: MatchContext,
    onApply: (MatchContext) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("게임 상태", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            // Dealer vs Non-Dealer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("친 (오야)")
                Switch(
                    checked = context.isDealer,
                    onCheckedChange = { onApply(context.copy(isDealer = it)) }
                )
            }

            // Tsumo vs Ron
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(if (context.isTsumo) "쯔모" else "론")
                Switch(
                    checked = context.isTsumo,
                    onCheckedChange = { onApply(context.copy(isTsumo = it)) }
                )
            }

            // Riichi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("리치")
                Switch(
                    checked = context.isRiichi,
                    onCheckedChange = { onApply(context.copy(isRiichi = it)) }
                )
            }

            // Dora Count Stepper
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("도라 수")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            if (context.doraCount > 0) {
                                onApply(context.copy(doraCount = context.doraCount - 1))
                            }
                        }
                    ) {
                        Text("-")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("${context.doraCount}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onApply(context.copy(doraCount = context.doraCount + 1))
                        }
                    ) {
                        Text("+")
                    }
                }
            }
        }
    }
}
