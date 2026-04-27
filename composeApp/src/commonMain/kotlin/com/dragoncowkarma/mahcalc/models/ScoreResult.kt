package com.dragoncowkarma.mahcalc.models

/**
 * Result of a calculated Mahjong score.
 */
data class ScoreResult(
    val totalHan: Int,
    val totalFu: Int,
    val yakuList: List<String>,
    val pointsToReceive: Int
)
