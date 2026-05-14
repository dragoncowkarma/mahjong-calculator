package com.dragoncowkarma.mahcalc.models

data class LocalRules(
    val akadoraEnabled: Boolean = false,
    val kuikaeProhibited: Boolean = false
)

/**
 * Context of a Mahjong match that affects the score of a hand.
 */
data class MatchContext(
    val isDealer: Boolean = false,
    val isTsumo: Boolean = false,
    val isRiichi: Boolean = false,
    val doraCount: Int = 0,
    val isFuriten: Boolean = false,
    val localRules: LocalRules = LocalRules()
)
