package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext

object MahjongCalculator {
    private val ORPHANS = intArrayOf(0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33)

    /**
     * Entry point to calculate Han and Fu for a given winning hand.
     * @param hand The 14 tiles of the winning hand
     * @param winningTile The tile that completed the hand
     * @param context The match context
     * @return Pair of <List of Yaku Names, Total Fu> or Pair(emptyList, 0) if invalid
     */
    fun calculate(hand: IntArray, winningTile: Int, context: MatchContext): Pair<List<String>, Int> {
        if (hand.size != 14) return Pair(emptyList(), 0)

        val counts = IntArray(34)
        for (tile in hand) {
            if (tile < 0 || tile > 33) return Pair(emptyList(), 0)
            counts[tile]++
            if (counts[tile] > 4) return Pair(emptyList(), 0)
        }

        // Special Hands (Kokushi Musou, Chiitoitsu)
        if (isKokushi(counts)) {
            val (yaku, _) = YakuCalculator.calculate(
                melds = emptyList(),
                waitType = WaitType.NO_WAIT,
                context = context,
                isKokushi = true,
                isChiitoitsu = false
            )
            // Yakuman has no Fu conceptually, but often scored as base 20, let's just return 0 or 20. We'll return 0 for Yakuman.
            return Pair(yaku, 0)
        }

        val chiitoitsuMelds = tryChiitoitsu(counts)
        if (chiitoitsuMelds != null) {
            val waitType = getChiitoitsuWait(counts, winningTile) // It's always Tanki for Chiitoitsu
            val (yaku, han) = YakuCalculator.calculate(
                melds = chiitoitsuMelds,
                waitType = waitType,
                context = context,
                isKokushi = false,
                isChiitoitsu = true
            )
            val fu = FuCalculator.calculate(
                melds = chiitoitsuMelds,
                waitType = waitType,
                winningTile = winningTile,
                context = context,
                isPinfu = false,
                isChiitoitsu = true
            )
            return Pair(yaku, fu)
        }

        // Standard Hand
        var bestHan = -1
        var bestFu = -1
        var bestYaku = emptyList<String>()

        val stateStack = IntArray(8) // choice, iAt

        for (i in 0..33) {
            if (counts[i] >= 2) {
                counts[i] -= 2

                // Iteratively find decompositions
                val decompositions = findDecompositions(counts.copyOf())

                for (decomp in decompositions) {
                    val melds = mutableListOf<Meld>()
                    melds.add(Meld(0, intArrayOf(i, i))) // Add the pair
                    melds.addAll(decomp)

                    val waitTypes = determineWaitTypes(melds, winningTile)
                    for (waitType in waitTypes) {
                        val isPinfu = YakuCalculator.isPinfu(melds, waitType)
                        val (yaku, han) = YakuCalculator.calculate(
                            melds = melds,
                            waitType = waitType,
                            context = context,
                            isKokushi = false,
                            isChiitoitsu = false
                        )
                        val fu = FuCalculator.calculate(
                            melds = melds,
                            waitType = waitType,
                            winningTile = winningTile,
                            context = context,
                            isPinfu = isPinfu,
                            isChiitoitsu = false
                        )

                        if (han > bestHan || (han == bestHan && fu > bestFu)) {
                            bestHan = han
                            bestFu = fu
                            bestYaku = yaku
                        }
                    }
                }

                counts[i] += 2
            }
        }

        if (bestHan == -1) return Pair(emptyList(), 0)
        return Pair(bestYaku, bestFu)
    }

    private fun isKokushi(counts: IntArray): Boolean {
        var hasPair = false
        for (i in ORPHANS) {
            val c = counts[i]
            if (c == 0) return false
            if (c == 2) {
                if (hasPair) return false
                hasPair = true
            } else if (c > 2) {
                return false
            }
        }
        return hasPair
    }

    private fun tryChiitoitsu(counts: IntArray): List<Meld>? {
        var pairs = 0
        val melds = mutableListOf<Meld>()
        for (i in counts.indices) {
            val c = counts[i]
            if (c == 2) {
                pairs++
                melds.add(Meld(0, intArrayOf(i, i)))
            } else if (c != 0) {
                return null
            }
        }
        return if (pairs == 7) melds else null
    }

    private fun getChiitoitsuWait(counts: IntArray, winningTile: Int): WaitType {
        return WaitType.TANKI
    }

    /**
     * Finds all valid decompositions of 4 melds from the remaining 12 tiles using an iterative state machine.
     */
    private fun findDecompositions(initialCounts: IntArray): List<List<Meld>> {
        val results = mutableListOf<List<Meld>>()
        val counts = initialCounts.copyOf()
        val stateStack = IntArray(8) // indices 0..3 for choice (0=uninit, 1=triplet, 2=sequence, 3=done), 4..7 for iAt
        val currentMelds = arrayOfNulls<Meld>(4)
        var depth = 0

        while (depth >= 0) {
            if (depth == 4) {
                results.add(currentMelds.filterNotNull().toList())
                depth-- // backtrack
                val prevI = stateStack[depth + 4]
                if (stateStack[depth] == 1) {
                    counts[prevI] += 3
                } else if (stateStack[depth] == 2) {
                    counts[prevI] += 1
                    counts[prevI + 1] += 1
                    counts[prevI + 2] += 1
                }
                continue
            }

            if (stateStack[depth] == 0) {
                var startI = 0
                while (startI < 34 && counts[startI] == 0) {
                    startI++
                }
                if (startI == 34) {
                    // This means we don't have enough tiles to form 4 melds. Backtrack.
                    stateStack[depth] = 0 // reset choice for this depth if we go back further
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
                    continue
                }
                stateStack[depth + 4] = startI
            }

            val i = stateStack[depth + 4]

            if (stateStack[depth] == 0) {
                stateStack[depth] = 1
                if (counts[i] >= 3) {
                    counts[i] -= 3
                    currentMelds[depth] = Meld(2, intArrayOf(i, i, i))
                    depth++
                    if (depth < 4) stateStack[depth] = 0
                    continue
                }
            }

            if (stateStack[depth] == 1) {
                stateStack[depth] = 2
                // sequence
                if (i < 27 && i % 9 <= 6 && counts[i] >= 1 && counts[i + 1] >= 1 && counts[i + 2] >= 1) {
                    counts[i] -= 1
                    counts[i + 1] -= 1
                    counts[i + 2] -= 1
                    currentMelds[depth] = Meld(1, intArrayOf(i, i + 1, i + 2))
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

        return results
    }

    private fun determineWaitTypes(melds: List<Meld>, winningTile: Int): List<WaitType> {
        val waits = mutableSetOf<WaitType>()
        var winningTileCount = 0
        for (m in melds) {
            for (t in m.tiles) {
                if (t == winningTile) winningTileCount++
            }
        }

        // Find which meld the winning tile belongs to.
        // It's possible it belongs to multiple melds if they share tiles.
        for (meld in melds) {
            if (!meld.tiles.contains(winningTile)) continue

            if (meld.type == 0) { // Pair -> Tanki
                waits.add(WaitType.TANKI)
            } else if (meld.type == 2) { // Triplet -> Shanpon (since it's a closed hand and completed by winning tile, it must have been a pair before winning)
                waits.add(WaitType.SHANPON)
            } else if (meld.type == 1) { // Sequence
                val sorted = meld.tiles.sorted()
                if (sorted[1] == winningTile) {
                    waits.add(WaitType.KANCHAN)
                } else if (sorted[0] == winningTile) {
                    if (sorted[2] % 9 == 8) { // e.g. winning on 7 for 789 -> Penchan
                        waits.add(WaitType.PENCHAN)
                    } else {
                        waits.add(WaitType.RYANMEN)
                    }
                } else if (sorted[2] == winningTile) {
                    if (sorted[0] % 9 == 0) { // e.g. winning on 3 for 123 -> Penchan
                        waits.add(WaitType.PENCHAN)
                    } else {
                        waits.add(WaitType.RYANMEN)
                    }
                }
            }
        }

        if (waits.isEmpty()) return listOf(WaitType.NO_WAIT) // Fallback
        return waits.toList()
    }
}
