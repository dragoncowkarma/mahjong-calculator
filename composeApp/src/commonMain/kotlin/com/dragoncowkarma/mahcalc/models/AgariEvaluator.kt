package com.dragoncowkarma.mahcalc.models

object AgariEvaluator {
    private val ORPHANS = intArrayOf(0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33)

    /**
     * Evaluates whether the given 14 tiles constitute a winning hand (Agari).
     * @param hand IntArray of 14 tile IDs (0-33).
     * @return true if it's a valid Agari hand, false otherwise.
     */
    fun isAgari(hand: IntArray): Boolean {
        if (hand.size != 14) return false

        // Allocate state arrays once per evaluation to minimize GC pressure.
        val counts = IntArray(34)
        val stateStack = IntArray(8) // indices 0..3 for 'choice', 4..7 for 'iAt'

        for (i in hand.indices) {
            val tile = hand[i]
            if (tile < 0 || tile > 33) return false
            counts[tile]++
            if (counts[tile] > 4) return false // A tile can appear at most 4 times
        }

        if (isKokushi(counts)) return true
        if (isChiitoitsu(counts)) return true

        // Check standard hand (4 melds + 1 pair)
        for (i in 0..33) {
            if (counts[i] >= 2) {
                counts[i] -= 2
                // Reset state stack for each pair attempt
                for (j in 0..7) stateStack[j] = 0

                if (checkMelds(counts, stateStack)) {
                    return true
                }
                counts[i] += 2
            }
        }

        return false
    }

    private fun isKokushi(counts: IntArray): Boolean {
        var hasPair = false
        for (i in ORPHANS.indices) {
            val c = counts[ORPHANS[i]]
            if (c == 0) return false
            if (c == 2) {
                if (hasPair) return false // can't have two pairs
                hasPair = true
            } else if (c > 2) {
                return false
            }
        }
        return hasPair
    }

    private fun isChiitoitsu(counts: IntArray): Boolean {
        var pairs = 0
        for (i in counts.indices) {
            val c = counts[i]
            if (c == 2) {
                pairs++
            } else if (c != 0) {
                return false // Standard rules: 7 distinct pairs
            }
        }
        return pairs == 7
    }

    private fun checkMelds(counts: IntArray, stateStack: IntArray): Boolean {
        var depth = 0

        while (depth >= 0) {
            if (depth == 4) return true

            if (stateStack[depth] == 0) { // choice[depth] == 0
                var startI = 0
                while (startI < 34 && counts[startI] == 0) {
                    startI++
                }
                if (startI == 34) return false
                stateStack[depth + 4] = startI // iAt[depth] = startI
            }

            val i = stateStack[depth + 4]

            if (stateStack[depth] == 0) {
                stateStack[depth] = 1
                if (counts[i] >= 3) {
                    counts[i] -= 3
                    depth++
                    if (depth < 4) stateStack[depth] = 0
                    continue
                }
            }

            if (stateStack[depth] == 1) {
                stateStack[depth] = 2
                // Sequence check: valid only for non-honor tiles, and sequence must not wrap (e.g., 8-9-1)
                // i % 9 <= 6 ensures it stays within the same suit
                if (i < 27 && i % 9 <= 6 && counts[i] >= 1 && counts[i + 1] >= 1 && counts[i + 2] >= 1) {
                    counts[i] -= 1
                    counts[i + 1] -= 1
                    counts[i + 2] -= 1
                    depth++
                    if (depth < 4) stateStack[depth] = 0
                    continue
                }
            }

            // Backtrack
            stateStack[depth] = 0
            depth--
            if (depth >= 0) {
                val prevI = stateStack[depth + 4]
                if (stateStack[depth] == 1) {
                    counts[prevI] += 3
                } else if (stateStack[depth] == 2) {
                    counts[prevI] += 1
                    counts[prevI + 1] += 1
                    counts[prevI + 2] += 1
                }
            }
        }

        return false
    }
}
