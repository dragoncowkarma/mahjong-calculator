package com.dragoncowkarma.mahcalc.models

object MockDataGenerator {
    /**
     * Provides a mock Tanyao hand as an IntArray of 14 tiles to avoid allocation overhead.
     * Contains 2-3-4 Man, 5-6-7 Pin, 2-3-4 Sou, 8-8 Sou, 6-7-8 Man.
     */
    val mockTanyaoHand: IntArray
        get() = intArrayOf(
            1, 2, 3, // 2-3-4 Man
            13, 14, 15, // 5-6-7 Pin
            19, 20, 21, // 2-3-4 Sou
            5, 6, 7, // 6-7-8 Man
            25, 25 // 8-8 Sou
        )

    val mockMatchContext: MatchContext
        get() = MatchContext(
            isDealer = false,
            isTsumo = true,
            isRiichi = true,
            doraCount = 1
        )

    val mockManganScoreResult: ScoreResult
        get() = ScoreResult(
            totalHan = 5,
            totalFu = 30,
            yakuList = listOf("Riichi", "Tsumo", "Tanyao", "Dora 2"),
            pointsToReceive = 8000
        )
}
