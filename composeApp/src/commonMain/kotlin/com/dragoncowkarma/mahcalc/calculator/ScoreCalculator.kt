package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext
import com.dragoncowkarma.mahcalc.models.ScoreResult

object ScoreCalculator {
    /**
     * Calculates the final points to receive based on Han, Fu, and MatchContext.
     */
    fun calculateScore(han: Int, fu: Int, context: MatchContext, yakuList: List<String> = emptyList()): ScoreResult {
        // Base points formula: Fu * 2^(Han + 2)
        var basePoints = 0
        val isYakuman = han >= 13

        if (isYakuman) {
            // Yakuman can stack if multiple, but we assume 1 yakuman = 13 han for base logic.
            // If han is 26, it's double yakuman, etc.
            val yakumanMultiplier = han / 13
            basePoints = 8000 * yakumanMultiplier
        } else {
            val calculatedBase = fu * (1 shl (han + 2))

            // Apply limits
            basePoints = when {
                han >= 11 -> 6000 // Sanbaiman
                han >= 8 -> 4000 // Baiman
                han >= 6 -> 3000 // Haneman
                han >= 5 || calculatedBase >= 2000 -> 2000 // Mangan
                else -> calculatedBase // Basic points
            }
        }

        var totalPointsToReceive = 0

        if (context.isTsumo) {
            if (context.isDealer) {
                // Dealer Tsumo: All 3 non-dealers pay 2 * basePoints
                val paymentPerPlayer = roundUpTo100(basePoints * 2)
                totalPointsToReceive = paymentPerPlayer * 3
            } else {
                // Non-dealer Tsumo: Dealer pays 2 * basePoints, 2 non-dealers pay basePoints
                val dealerPayment = roundUpTo100(basePoints * 2)
                val nonDealerPayment = roundUpTo100(basePoints)
                totalPointsToReceive = dealerPayment + (nonDealerPayment * 2)
            }
        } else {
            // Ron
            if (context.isDealer) {
                // Dealer Ron: discarding player pays 6 * basePoints
                totalPointsToReceive = roundUpTo100(basePoints * 6)
            } else {
                // Non-dealer Ron: discarding player pays 4 * basePoints
                totalPointsToReceive = roundUpTo100(basePoints * 4)
            }
        }

        return ScoreResult(
            totalHan = han,
            totalFu = fu,
            yakuList = yakuList,
            pointsToReceive = totalPointsToReceive
        )
    }

    private fun roundUpTo100(value: Int): Int {
        return (value + 99) / 100 * 100
    }
}
