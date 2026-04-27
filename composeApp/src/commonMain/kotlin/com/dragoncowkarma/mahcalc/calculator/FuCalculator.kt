package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext

object FuCalculator {
    /**
     * Calculates the Fu (minipoints) for a hand.
     *
     * @param melds List of melds and the pair making up the hand
     * @param waitType The wait type of the winning tile
     * @param winningTile The ID of the winning tile
     * @param context The match context (tsumo, ron, etc.)
     * @param isPinfu True if the hand qualifies for the Pinfu yaku
     * @return The calculated Fu, rounded up to the nearest 10
     */
    fun calculate(
        melds: List<Meld>,
        waitType: WaitType,
        winningTile: Int,
        context: MatchContext,
        isPinfu: Boolean,
        isChiitoitsu: Boolean
    ): Int {
        if (isChiitoitsu) {
            return 25 // Chiitoitsu is strictly 25 fu, no rounding
        }

        var fu = 20 // Base fu

        // Pinfu specific rules
        if (isPinfu) {
            if (context.isTsumo) {
                return 20 // Pinfu Tsumo is always 20 fu
            } else {
                return 30 // Pinfu Ron is always 30 fu
            }
        }

        // Win condition fu
        if (context.isTsumo) {
            fu += 2
        } else if (!context.isTsumo) { // Menzen Ron is +10, but we don't have open hand concept yet, assume all closed for now based on context
            fu += 10
        }

        // Wait type fu
        when (waitType) {
            WaitType.KANCHAN, WaitType.PENCHAN, WaitType.TANKI -> fu += 2
            else -> {}
        }

        // Melds fu
        for (meld in melds) {
            if (meld.type == 0) { // Pair
                val tile = meld.tiles[0]
                if (isValueHonor(tile)) {
                    fu += 2 // We simplify seat wind vs round wind. Assume it adds 2 fu if it's a dragon or any wind for now, or just dragons to be safer.
                    // For a true implementation, we need seat wind and round wind in MatchContext.
                    // Assuming dragons are 31, 32, 33 (White, Green, Red)
                    // Let's implement value honor check.
                }
            } else if (meld.type == 2) { // Triplet
                val tile = meld.tiles[0]
                val isTerminalOrHonor = tile == 0 || tile == 8 || tile == 9 || tile == 17 || tile == 18 || tile == 26 || tile in 27..33
                // Closed triplet
                fu += if (isTerminalOrHonor) 8 else 4
                // Note: Open triplets would be 4 or 2, Kan would be 32/16/16/8.
                // We're simplifying by assuming closed triplets since we don't have open/closed meld state yet.
            }
        }

        // Round up to nearest 10
        return (fu + 9) / 10 * 10
    }

    private fun isValueHonor(tileId: Int): Boolean {
        // Assuming:
        // 27: East, 28: South, 29: West, 30: North
        // 31: White, 32: Green, 33: Red
        // Without round/seat winds, we'll consider all dragons as value,
        // and optionally East/South if dealer context implies it.
        // For simplicity in this base version, only Dragons are definitely value honors without more context.
        return tileId in 31..33
    }
}
