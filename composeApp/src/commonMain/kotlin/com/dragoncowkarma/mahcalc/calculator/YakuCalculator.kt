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
            yakuList.add("Kokushi Musou")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        if (isSuuankou(melds)) {
            yakuList.add("Suuankou")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        if (isDaisangen(melds)) {
            yakuList.add("Daisangen")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        if (isTsuuiisou(melds)) {
            yakuList.add("Tsuuiisou")
            totalHan += 13
            return Pair(yakuList, totalHan)
        }

        // Standard Yaku
        if (context.isRiichi) {
            yakuList.add("Riichi")
            totalHan += 1
        }

        if (context.isTsumo) { // Assuming fully closed hand for now
            yakuList.add("Menzen Tsumo")
            totalHan += 1
        }

        if (isTanyao(melds, isChiitoitsu)) {
            yakuList.add("Tanyao")
            totalHan += 1
        }

        if (!isChiitoitsu && isPinfu(melds, waitType)) {
            yakuList.add("Pinfu")
            totalHan += 1
        }

        if (isChiitoitsu) {
            yakuList.add("Chiitoitsu")
            totalHan += 2
        }

        val yakuhaiCount = countYakuhai(melds)
        if (yakuhaiCount > 0) {
            yakuList.add("Yakuhai")
            totalHan += yakuhaiCount
        }

        if (isSanankou(melds)) {
            yakuList.add("Sanankou")
            totalHan += 2
        }

        if (isToitoi(melds)) {
            yakuList.add("Toitoi")
            totalHan += 2
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
}
