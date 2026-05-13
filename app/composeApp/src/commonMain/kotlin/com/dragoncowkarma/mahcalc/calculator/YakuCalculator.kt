package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext

object YakuCalculator {
    private val TERMINALS_AND_HONORS = intArrayOf(0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33)

    /**
     * Calculates the Yaku for a hand.
     * @return Pair of <List of Yaku Names, Total Han>
     */
    fun calculate(
        melds: List<Meld>,
        waitType: WaitType,
        context: MatchContext,
        isKokushi: Boolean,
        isChiitoitsu: Boolean
    ): Pair<List<String>, Int> {
        val yakuList = mutableListOf<String>()
        var totalHan = 0

        // Yakuman checks
        if (isKokushi) {
            yakuList.add("국사무쌍")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        if (isSuuankou(melds)) {
            yakuList.add("사안커")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        if (isDaisangen(melds)) {
            yakuList.add("대삼원")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        if (isTsuuiisou(melds)) {
            yakuList.add("자일색")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        // Standard Yaku
        if (context.isRiichi) {
            yakuList.add("리치")
            totalHan += 1
        }

        if (context.isTsumo) { // Assuming fully closed hand for now
            yakuList.add("멘젠쯔모")
            totalHan += 1
        }

        if (isTanyao(melds, isChiitoitsu)) {
            yakuList.add("탕야오")
            totalHan += 1
        }

        if (!isChiitoitsu && isPinfu(melds, waitType)) {
            yakuList.add("핑후")
            totalHan += 1
        }

        if (isChiitoitsu) {
            yakuList.add("치또이츠")
            totalHan += 2
        }

        val yakuhaiCount = countYakuhai(melds)
        if (yakuhaiCount > 0) {
            for (i in 0 until yakuhaiCount) {
                yakuList.add("역패")
            }
            totalHan += yakuhaiCount
        }

        if (isSanankou(melds)) {
            yakuList.add("산안커")
            totalHan += 2
        }

        if (isToitoi(melds)) {
            yakuList.add("또이또이")
            totalHan += 2
        }

        if (isSanshokuDoujun(melds)) {
            yakuList.add("산쇼쿠도쥰 (삼색동순)")
            totalHan += 2 // Assuming closed for now
        }

        if (isShousangen(melds)) {
            yakuList.add("소삼원")
            totalHan += 2
        }

        if (isHonroto(melds)) {
            yakuList.add("혼로토")
            totalHan += 2
        }

        if (isChinitsu(melds)) {
            yakuList.add("청일색")
            totalHan += 6
        } else if (isHonitsu(melds)) {
            yakuList.add("혼일색")
            totalHan += 3
        }

        return Pair(yakuList, totalHan)
    }

    private fun isTanyao(melds: List<Meld>, isChiitoitsu: Boolean): Boolean {
        for (meld in melds) {
            for (tile in meld.tiles) {
                if (tile in TERMINALS_AND_HONORS) {
                    return false
                }
            }
        }
        return true
    }

    fun isPinfu(melds: List<Meld>, waitType: WaitType): Boolean {
        if (waitType != WaitType.RYANMEN) return false

        var sequences = 0
        var pairValueHonor = false

        for (meld in melds) {
            if (meld.type == 1) { // Sequence
                sequences++
            } else if (meld.type == 0) { // Pair
                val tile = meld.tiles[0]
                // 31,32,33 are Dragons.
                // Simplified: we'll treat Dragons as value honors.
                // In a full implementation, we'd check seat/round wind.
                if (tile in 31..33) {
                    pairValueHonor = true
                }
            } else {
                return false // Contains triplet/kan
            }
        }

        return sequences == 4 && !pairValueHonor
    }

    private fun countYakuhai(melds: List<Meld>): Int {
        var count = 0
        for (meld in melds) {
            if (meld.type == 2) {
                val tile = meld.tiles[0]
                if (tile in 31..33) { // Dragons
                    count++
                }
            }
        }
        return count
    }

    private fun isSanankou(melds: List<Meld>): Boolean {
        // Since we assume closed hands, any 3 triplets is Sanankou
        var triplets = 0
        for (meld in melds) {
            if (meld.type == 2) triplets++
        }
        return triplets == 3
    }

    private fun isToitoi(melds: List<Meld>): Boolean {
        var triplets = 0
        for (meld in melds) {
            if (meld.type == 2) triplets++
        }
        return triplets == 4
    }

    private fun isSuuankou(melds: List<Meld>): Boolean {
        var triplets = 0
        for (meld in melds) {
            if (meld.type == 2) triplets++
        }
        return triplets == 4
    }

    private fun isDaisangen(melds: List<Meld>): Boolean {
        var dragons = 0
        for (meld in melds) {
            if (meld.type == 2 && meld.tiles[0] in 31..33) {
                dragons++
            }
        }
        return dragons == 3
    }

    private fun isTsuuiisou(melds: List<Meld>): Boolean {
        for (meld in melds) {
            for (tile in meld.tiles) {
                if (tile !in 27..33) return false
            }
        }
        return true
    }

    private fun isSanshokuDoujun(melds: List<Meld>): Boolean {
        val sequences = melds.filter { it.type == 1 }
        if (sequences.size < 3) return false

        // Extract the lowest number of each sequence (1-9) and its suit
        // Suit: Man(0-8), Pin(9-17), Sou(18-26)
        val seqInfo = sequences.map { meld ->
            val startTile = meld.tiles.minOrNull() ?: 0
            val number = (startTile % 9) + 1
            val suit = startTile / 9 // 0=Man, 1=Pin, 2=Sou
            Pair(number, suit)
        }

        // Check if there are 3 sequences with the same number but different suits (0, 1, and 2)
        val groupedByNumber = seqInfo.groupBy { it.first }
        for ((_, group) in groupedByNumber) {
            val suits = group.map { it.second }.toSet()
            if (suits.contains(0) && suits.contains(1) && suits.contains(2)) {
                return true
            }
        }

        return false
    }

    private fun isShousangen(melds: List<Meld>): Boolean {
        var dragonTriplets = 0
        var dragonPair = 0
        for (meld in melds) {
            if (meld.type == 2 && meld.tiles[0] in 31..33) dragonTriplets++
            if (meld.type == 0 && meld.tiles[0] in 31..33) dragonPair++
        }
        return dragonTriplets == 2 && dragonPair == 1
    }

    private fun isHonroto(melds: List<Meld>): Boolean {
        for (meld in melds) {
            for (tile in meld.tiles) {
                if (tile !in TERMINALS_AND_HONORS) return false
            }
        }
        return true
    }

    private fun isHonitsu(melds: List<Meld>): Boolean {
        var hasHonor = false
        var suit = -1
        for (meld in melds) {
            for (tile in meld.tiles) {
                if (tile in 27..33) {
                    hasHonor = true
                } else {
                    val currentSuit = tile / 9
                    if (suit == -1) {
                        suit = currentSuit
                    } else if (suit != currentSuit) {
                        return false
                    }
                }
            }
        }
        return hasHonor && suit != -1
    }

    private fun isChinitsu(melds: List<Meld>): Boolean {
        var suit = -1
        for (meld in melds) {
            for (tile in meld.tiles) {
                if (tile in 27..33) return false
                val currentSuit = tile / 9
                if (suit == -1) {
                    suit = currentSuit
                } else if (suit != currentSuit) {
                    return false
                }
            }
        }
        return suit != -1
    }
}
