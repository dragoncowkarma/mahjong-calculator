package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class YakuCalculatorTest {

    @Test
    fun testSanshokuDoujun() {
        val melds = listOf(
            Meld(1, intArrayOf(1, 2, 3)), // 2-3-4 Man
            Meld(1, intArrayOf(10, 11, 12)), // 2-3-4 Pin
            Meld(1, intArrayOf(19, 20, 21)), // 2-3-4 Sou
            Meld(2, intArrayOf(27, 27, 27)), // East triplet
            Meld(0, intArrayOf(33, 33)) // Red dragon pair
        )
        val context = MatchContext()
        val (yaku, han) = YakuCalculator.calculate(
            melds = melds,
            waitType = WaitType.RYANMEN,
            context = context,
            isKokushi = false,
            isChiitoitsu = false
        )

        assertTrue(yaku.contains("산쇼쿠도쥰 (삼색동순)"))
        assertTrue(yaku.contains("탕야오").not()) // East and Red are honors
    }

    @Test
    fun testTsuuiisouChiitoitsu() {
        // Honroto/Tsuuiisou Chiitoitsu format
        val melds = listOf(
            Meld(0, intArrayOf(27, 27)), // East
            Meld(0, intArrayOf(28, 28)), // South
            Meld(0, intArrayOf(29, 29)), // West
            Meld(0, intArrayOf(30, 30)), // North
            Meld(0, intArrayOf(31, 31)), // Haku
            Meld(0, intArrayOf(32, 32)), // Hatsu
            Meld(0, intArrayOf(33, 33))  // Chun
        )
        val context = MatchContext()
        val (yaku, han) = YakuCalculator.calculate(
            melds = melds,
            waitType = WaitType.TANKI,
            context = context,
            isKokushi = false,
            isChiitoitsu = true
        )

        // It should bypass Suuankou and Daisangen and hit Tsuuiisou
        assertTrue(yaku.contains("자일색"))
        assertEquals(13, han)
    }

    @Test
    fun testPinfu() {
        val melds = listOf(
            Meld(1, intArrayOf(1, 2, 3)), // 234 Man
            Meld(1, intArrayOf(4, 5, 6)), // 567 Man
            Meld(1, intArrayOf(10, 11, 12)), // 234 Pin
            Meld(1, intArrayOf(19, 20, 21)), // 234 Sou
            Meld(0, intArrayOf(27, 27)) // East Pair
        )
        val context = MatchContext()
        val (yaku, han) = YakuCalculator.calculate(
            melds = melds,
            waitType = WaitType.RYANMEN,
            context = context,
            isKokushi = false,
            isChiitoitsu = false
        )

        assertTrue(yaku.contains("핑후"))
    }

    @Test
    fun testSanankou() {
        val melds = listOf(
            Meld(2, intArrayOf(1, 1, 1)), // 2 Man Triplet
            Meld(2, intArrayOf(10, 10, 10)), // 2 Pin Triplet
            Meld(2, intArrayOf(19, 19, 19)), // 2 Sou Triplet
            Meld(1, intArrayOf(4, 5, 6)), // 567 Man Sequence
            Meld(0, intArrayOf(27, 27)) // East pair
        )
        val context = MatchContext()
        val (yaku, han) = YakuCalculator.calculate(
            melds = melds,
            waitType = WaitType.SHANPON,
            context = context,
            isKokushi = false,
            isChiitoitsu = false
        )

        assertTrue(yaku.contains("산안커"))
    }

    @Test
    fun testDaisangen() {
        val melds = listOf(
            Meld(2, intArrayOf(31, 31, 31)), // Haku (White Dragon) Triplet
            Meld(2, intArrayOf(32, 32, 32)), // Hatsu (Green Dragon) Triplet
            Meld(2, intArrayOf(33, 33, 33)), // Chun (Red Dragon) Triplet
            Meld(1, intArrayOf(1, 2, 3)),    // 2-3-4 Man Sequence
            Meld(0, intArrayOf(10, 10))      // 2 Pin Pair
        )
        val context = MatchContext()
        val (yaku, han) = YakuCalculator.calculate(
            melds = melds,
            waitType = WaitType.SHANPON,
            context = context,
            isKokushi = false,
            isChiitoitsu = false
        )

        assertTrue(yaku.contains("대삼원"))
        assertEquals(13, han)
    }

    @Test
    fun testYakuhai() {
        val melds = listOf(
            Meld(2, intArrayOf(33, 33, 33)), // Chun (Red Dragon) Triplet
            Meld(1, intArrayOf(1, 2, 3)),    // 2-3-4 Man Sequence
            Meld(1, intArrayOf(10, 11, 12)), // 2-3-4 Pin Sequence
            Meld(1, intArrayOf(20, 21, 22)), // 3-4-5 Sou Sequence
            Meld(0, intArrayOf(27, 27))      // East pair
        )
        val context = MatchContext()
        val (yaku, han) = YakuCalculator.calculate(
            melds = melds,
            waitType = WaitType.RYANMEN,
            context = context,
            isKokushi = false,
            isChiitoitsu = false
        )

        assertTrue(yaku.contains("역패"))
        // 1 Han for Yakuhai
        assertEquals(1, han)
    }
}
