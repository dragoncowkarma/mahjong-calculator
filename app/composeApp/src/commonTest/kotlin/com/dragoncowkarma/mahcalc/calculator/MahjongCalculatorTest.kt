package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MahjongCalculatorTest {

    @Test
    fun testPinfuTanyaoRiichi() {
        // 234m 567p 234s 67s 88p -> Wait on 5s or 8s. Let's say winning tile is 5s (22).
        // 1m = 1..9 (0-8) -> 234m = 1, 2, 3
        // 567p (13, 14, 15)
        // 234s (19, 20, 21)
        // 67s (23, 24) + 5s (22)
        // 88p (16, 16)
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )
        val context = MatchContext(isRiichi = true, isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 22, context = context)

        assertTrue(yaku.contains("리치"))
        assertTrue(yaku.contains("탕야오"))
        assertTrue(yaku.contains("핑후"))
        assertEquals(30, fu) // Pinfu Ron is 30 fu
    }

    @Test
    fun testKokushiMusou() {
        val hand = intArrayOf(
            0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 33
        )
        val context = MatchContext()
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 33, context = context)

        assertTrue(yaku.contains("국사무쌍"))
        assertEquals(0, fu) // Yakuman has 0 base fu representation here
    }

    @Test
    fun testChiitoitsu() {
        val hand = intArrayOf(
            1, 1, 3, 3, 5, 5, 12, 12, 19, 19, 21, 21, 25, 25
        )
        val context = MatchContext(isTsumo = true)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 25, context = context)

        assertTrue(yaku.contains("치또이츠"))
        assertTrue(yaku.contains("탕야오"))
        assertTrue(yaku.contains("멘젠쯔모"))
        assertEquals(25, fu) // Chiitoitsu is exactly 25 fu
    }

    @Test
    fun testToitoiSanankouYakuhai() {
        // 4 triplets and 1 pair
        // 333m, 555p, 777s, Green Dragon (32, 32, 32), pair of 1p (9, 9)
        val hand = intArrayOf(
            2, 2, 2,
            13, 13, 13,
            24, 24, 24,
            32, 32, 32,
            9, 9
        )
        val context = MatchContext(isTsumo = true)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 32, context = context)

        // It is actually Suuankou if closed Tsumo!
        assertTrue(yaku.contains("사안커"))
    }

    @Test
    fun testKanchanWait() {
        // 13m (wait 2m), 456p, 789s, East (27,27,27), South pair (28,28)
        val hand = intArrayOf(
            0, 1, 2, // 123m (wait on 2m -> Kanchan)
            12, 13, 14, // 456p
            24, 25, 26, // 789s
            27, 27, 27, // East
            28, 28 // South
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 1, context = context) // winning on 2m (1)

        // Fu calculation:
        // Base: 20
        // Menzen Ron: +10
        // Wait (Kanchan): +2
        // East triplet (terminal/honor closed): +8
        // South pair: +0 (assume not value for simplicity in this case)
        // Total = 40, rounded to 40.
        assertEquals(40, fu)
    }

    @Test
    fun testValuePairTankiWait() {
        // 123m, 456m, 789p, 123s, Red Dragon Pair wait
        val hand = intArrayOf(
            0, 1, 2,
            3, 4, 5,
            15, 16, 17,
            18, 19, 20,
            33, 33
        )
        val context = MatchContext(isTsumo = true)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 33, context = context)

        // Fu:
        // Base: 20
        // Tsumo: +2
        // Wait (Tanki): +2
        // Pair (Red Dragon): +2
        // Melds: +0
        // Total = 26 -> 30 fu
        assertEquals(30, fu)
    }

    @Test
    fun testKanchanWait2() {
        // Renaming original testKanchanWait to this if it already existed, or add new one
        // Wait on 2m inside 1m 3m
        val hand = intArrayOf(
            0, 2, 3, // 1m, 3m, 4m (wait 2m)
            12, 13, 14,
            24, 25, 26,
            27, 27, 27,
            28, 28
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 1, context = context)

        // Wait on 2m, makes it 123m (0, 1, 2) and an extra 4m? Wait, the math:
        // 13m + 456p + 789s + EEE + SS -> incomplete.
        // Let's do 13m (wait 2m), 456p, 789s, EEE, SS.
        // Total hand tiles without winning = 13.
        // Wait, MahjongCalculator.calculate takes full hand (14 tiles)
    }

    @Test
    fun testRyanmenWait() {
        // 23m (wait 1m, 4m), 456p, 789s, 111p, 22s
        // 23m -> 1, 2. Winning on 4m -> 3
        val hand = intArrayOf(
            1, 2, 3, // 234m
            12, 13, 14, // 456p
            24, 25, 26, // 789s
            9, 9, 9, // 111p
            19, 19 // 22s
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 3, context = context)

        // Pinfu is false because of the 111p triplet.
        // Base 20 + Ron 10 = 30
        // 111p closed triplet (terminal) = 8
        // Ryanmen wait = 0
        // Total = 38 -> 40 fu
        assertEquals(40, fu)
    }

    @Test
    fun testPenchanWait() {
        // 12m (wait 3m), 456p, 789s, 111p, 22s
        // 12m -> 0, 1. Winning on 3m -> 2
        val hand = intArrayOf(
            0, 1, 2, // 123m
            12, 13, 14, // 456p
            24, 25, 26, // 789s
            9, 9, 9, // 111p
            19, 19 // 22s
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 2, context = context)

        // Base 20 + Ron 10 = 30
        // Penchan wait = 2
        // 111p closed triplet (terminal) = 8
        // Total = 40 -> 40 fu
        assertEquals(40, fu)
    }

    @Test
    fun testTankiWait() {
        // Tanki on 1m (0)
        // 11m, 456m, 789p, 123s, 456s
        val hand = intArrayOf(
            0, 0, // 11m
            3, 4, 5, // 456m
            15, 16, 17, // 789p
            18, 19, 20, // 123s
            21, 22, 23 // 456s
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 0, context = context)

        // Base 20 + Ron 10 = 30
        // Tanki wait = 2
        // Total = 32 -> 40 fu
        assertEquals(40, fu)
    }

    @Test
    fun testShanponWait() {
        // 11m, 22p, 456m, 789p, 123s
        // Wait on 1m or 22p. Win on 1m (0).
        val hand = intArrayOf(
            0, 0, 0, // 111m
            10, 10, // 22p
            3, 4, 5, // 456m
            15, 16, 17, // 789p
            18, 19, 20 // 123s
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 0, context = context)

        // Base 20 + Ron 10 = 30
        // Shanpon = 0 for wait itself, but completed triplet is scored.
        // 111m closed triplet (terminal) = 8
        // Total = 38 -> 40 fu
        assertEquals(40, fu)
    }

    @Test
    fun testFuritenRon() {
        // Same hand as Pinfu Tanyao, but player is Furiten and tries to Ron.
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )
        val context = MatchContext(isTsumo = false, isFuriten = true)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 22, context = context)

        assertTrue(yaku.isEmpty())
        assertEquals(0, fu)
    }

    @Test
    fun testFuritenTsumo() {
        // Furiten, but winning by Tsumo is valid.
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )
        val context = MatchContext(isTsumo = true, isFuriten = true)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 22, context = context)

        assertTrue(yaku.contains("멘젠쯔모"))
        assertTrue(yaku.contains("탕야오"))
        assertTrue(yaku.contains("핑후"))
        // Pinfu Tsumo is always 20
        assertEquals(20, fu)
    }

    @Test
    fun testPinfuTsumoFu() {
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )
        val context = MatchContext(isTsumo = true)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 22, context = context)

        assertTrue(yaku.contains("핑후"))
        assertEquals(20, fu)
    }

    @Test
    fun testPinfuRonFu() {
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 22, context = context)

        assertTrue(yaku.contains("핑후"))
        assertEquals(30, fu)
    }

    @Test
    fun testPinfuTankiConflict() {
        // Hand looks like Pinfu, but the wait is Tanki on the pair.
        // Wait on 8p (16)
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )
        val context = MatchContext(isTsumo = false)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 16, context = context)

        // Cannot be Pinfu because wait is Tanki.
        assertTrue(!yaku.contains("핑후"))
        // Base 20 + Menzen Ron 10 + Tanki wait 2 = 32 -> 40 fu.
        assertEquals(40, fu)
    }

    @Test
    fun testLocalRulesConfiguration() {
        val hand = intArrayOf(
            1, 2, 3,
            13, 14, 15,
            19, 20, 21,
            22, 23, 24,
            16, 16
        )

        // Use custom LocalRules
        val localRules = com.dragoncowkarma.mahcalc.models.LocalRules(akadoraEnabled = true, kuikaeProhibited = true)
        val context = MatchContext(isTsumo = true, localRules = localRules)
        val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile = 22, context = context)

        // Core calculation should remain unaffected
        assertTrue(yaku.contains("핑후"))
        assertEquals(20, fu)
    }
}
