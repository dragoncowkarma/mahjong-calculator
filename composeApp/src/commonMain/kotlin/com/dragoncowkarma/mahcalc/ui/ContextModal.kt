package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dragoncowkarma.mahcalc.models.MatchContext



@Composable
fun ContextModal(
    context: MatchContext,
    onApply: (MatchContext) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text("Game State")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Dealer vs Non-Dealer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dealer")
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
                    Text("Tsumo")
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
                    Text("Riichi")
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
                    Text("Dora Count")
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
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ContextModalPreview() {
    MaterialTheme {
        ContextModal(
            context = MatchContext(
                isDealer = true,
                isTsumo = false,
                isRiichi = true,
                doraCount = 3
            ),
            onApply = {},
            onDismissRequest = {}
        )
    }
}
