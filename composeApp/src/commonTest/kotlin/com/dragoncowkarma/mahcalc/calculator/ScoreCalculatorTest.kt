package com.dragoncowkarma.mahcalc.calculator

import com.dragoncowkarma.mahcalc.models.MatchContext
import kotlin.test.Test
import kotlin.test.assertEquals

class ScoreCalculatorTest {

    @Test
    fun testNonDealerRon1Han30Fu() {
        val context = MatchContext(isDealer = false, isTsumo = false)
        val result = ScoreCalculator.calculateScore(han = 1, fu = 30, context = context)
        // 30 * 2^(1+2) = 30 * 8 = 240
        // 240 * 4 = 960 -> round up to 1000
        assertEquals(1000, result.pointsToReceive)
    }

    @Test
    fun testDealerRon1Han40Fu() {
        val context = MatchContext(isDealer = true, isTsumo = false)
        val result = ScoreCalculator.calculateScore(han = 1, fu = 40, context = context)
        // 40 * 2^(1+2) = 40 * 8 = 320
        // 320 * 6 = 1920 -> round up to 2000
        assertEquals(2000, result.pointsToReceive)
    }

    @Test
    fun testNonDealerTsumo3Han20Fu() {
        // Typically Pinfu Tsumo
        val context = MatchContext(isDealer = false, isTsumo = true)
        val result = ScoreCalculator.calculateScore(han = 3, fu = 20, context = context)
        // 20 * 2^(3+2) = 20 * 32 = 640
        // Dealer pays: roundUp(640 * 2) = roundUp(1280) = 1300
        // Non-dealers pay: roundUp(640) = roundUp(640) = 700
        // Total = 1300 + 700 + 700 = 2700
        assertEquals(2700, result.pointsToReceive)
    }

    @Test
    fun testDealerTsumo4Han30Fu() {
        val context = MatchContext(isDealer = true, isTsumo = true)
        val result = ScoreCalculator.calculateScore(han = 4, fu = 30, context = context)
        // 30 * 2^(4+2) = 30 * 64 = 1920
        // Base points is NOT capped at mangan because 1920 < 2000.
        // Payment per player: roundUp(1920 * 2) = roundUp(3840) = 3900
        // Total = 3900 * 3 = 11700
        assertEquals(11700, result.pointsToReceive)
    }

    @Test
    fun testNonDealerRon4Han30Fu() {
        val context = MatchContext(isDealer = false, isTsumo = false)
        val result = ScoreCalculator.calculateScore(han = 4, fu = 30, context = context)
        // Base: 1920
        // 1920 * 4 = 7680 -> round up to 7700
        assertEquals(7700, result.pointsToReceive)
    }

    @Test
    fun testManganDealerRon() {
        val context = MatchContext(isDealer = true, isTsumo = false)
        // 5 han is guaranteed Mangan (base 2000)
        val result = ScoreCalculator.calculateScore(han = 5, fu = 20, context = context)
        // 2000 * 6 = 12000 -> round up to 12000
        assertEquals(12000, result.pointsToReceive)
    }

    @Test
    fun testManganNonDealerTsumo() {
        val context = MatchContext(isDealer = false, isTsumo = true)
        // 4 han 40 fu is Mangan (40 * 64 = 2560 >= 2000)
        val result = ScoreCalculator.calculateScore(han = 4, fu = 40, context = context)
        // Base = 2000
        // Dealer pays: roundUp(4000) = 4000
        // Non-dealers pay: roundUp(2000) = 2000
        // Total = 4000 + 2000 + 2000 = 8000
        assertEquals(8000, result.pointsToReceive)
    }

    @Test
    fun testHanemanDealerTsumo() {
        val context = MatchContext(isDealer = true, isTsumo = true)
        val result = ScoreCalculator.calculateScore(han = 6, fu = 30, context = context)
        // Base = 3000
        // Each pays: roundUp(6000) = 6000
        // Total = 18000
        assertEquals(18000, result.pointsToReceive)
    }

    @Test
    fun testYakumanNonDealerRon() {
        val context = MatchContext(isDealer = false, isTsumo = false)
        val result = ScoreCalculator.calculateScore(han = 13, fu = 0, context = context)
        // Base = 8000
        // 8000 * 4 = 32000 -> round up to 32000
        assertEquals(32000, result.pointsToReceive)
    }

    @Test
    fun testDoubleYakumanDealerTsumo() {
        val context = MatchContext(isDealer = true, isTsumo = true)
        val result = ScoreCalculator.calculateScore(han = 26, fu = 0, context = context)
        // Multiplier = 2
        // Base = 16000
        // Each pays: roundUp(32000) = 32000
        // Total = 96000
        assertEquals(96000, result.pointsToReceive)
    }
}
