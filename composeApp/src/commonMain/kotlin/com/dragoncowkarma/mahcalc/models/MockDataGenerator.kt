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

    val mockBoundingBoxes: List<BoundingBox>
        get() = listOf(
            BoundingBox(10f, 10f, 20f, 30f, 0.95f, 1),
            BoundingBox(12f, 11f, 18f, 28f, 0.85f, 1), // Overlapping with first
            BoundingBox(50f, 50f, 20f, 30f, 0.90f, 2),
            BoundingBox(100f, 100f, 20f, 30f, 0.88f, 3),
            BoundingBox(98f, 102f, 22f, 28f, 0.70f, 3) // Overlapping with fourth
        )
}
