package com.dragoncowkarma.mahcalc

import com.dragoncowkarma.mahcalc.models.AgariEvaluator
import com.dragoncowkarma.mahcalc.models.MockDataGenerator
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AgariEvaluatorTest {

    @Test
    fun testValidTanyaoHand() {
        val hand = MockDataGenerator.mockTanyaoHand
        assertTrue(AgariEvaluator.isAgari(hand), "Tanyao hand should be a valid Agari")
    }

    @Test
    fun testValidKokushiMusou() {
        // 13 orphans + 1 pair of East (27)
        val hand = intArrayOf(
            0, 8, // 1, 9 Man
            9, 17, // 1, 9 Pin
            18, 26, // 1, 9 Sou
            27, 27, 28, 29, 30, 31, 32, 33 // Honors
        )
        assertTrue(AgariEvaluator.isAgari(hand), "Kokushi Musou should be valid")
    }

    @Test
    fun testValidChiitoitsu() {
        // 7 pairs
        val hand = intArrayOf(
            0, 0,
            1, 1,
            9, 9,
            10, 10,
            18, 18,
            27, 27,
            33, 33
        )
        assertTrue(AgariEvaluator.isAgari(hand), "Chiitoitsu should be valid")
    }

    @Test
    fun testValidStandardHandAllTriplets() {
        // 4 triplets + 1 pair
        val hand = intArrayOf(
            0, 0, 0, // 1 Man
            9, 9, 9, // 1 Pin
            18, 18, 18, // 1 Sou
            27, 27, 27, // East
            33, 33 // Red
        )
        assertTrue(AgariEvaluator.isAgari(hand), "All triplets hand should be valid")
    }

    @Test
    fun testInvalidHandTooFewTiles() {
        val hand = intArrayOf(0, 0, 0, 1, 2, 3, 9, 9, 9, 18, 18, 18, 27) // 13 tiles
        assertFalse(AgariEvaluator.isAgari(hand), "Hand with 13 tiles should be invalid")
    }

    @Test
    fun testInvalidHandTooManyTiles() {
        val hand = intArrayOf(0, 0, 0, 1, 2, 3, 9, 9, 9, 18, 18, 18, 27, 27, 27) // 15 tiles
        assertFalse(AgariEvaluator.isAgari(hand), "Hand with 15 tiles should be invalid")
    }

    @Test
    fun testInvalidHandSingleTiles() {
        // 14 distinct tiles, no pairs
        val hand = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
        assertFalse(AgariEvaluator.isAgari(hand), "Hand with 14 distinct tiles should be invalid")
    }

    @Test
    fun testInvalidHandFiveOfAKind() {
        val hand = intArrayOf(0, 0, 0, 0, 0, 1, 2, 3, 9, 9, 9, 27, 27, 27)
        assertFalse(AgariEvaluator.isAgari(hand), "Hand with 5 identical tiles should be invalid")
    }

    @Test
    fun testInvalidSequenceWrappingSuit() {
        // Tries to make a sequence 8-9-1 across suits: 8 Man (7), 9 Man (8), 1 Pin (9)
        // Hand: 1 pair (East 27,27), 3 valid triplets (18,18,18), (30,30,30), (33,33,33), 1 invalid sequence (7, 8, 9)
        val hand = intArrayOf(
            27, 27, // Pair East
            18, 18, 18, // 1 Sou triplet
            30, 30, 30, // North triplet
            33, 33, 33, // Red triplet
            7, 8, 9 // 8 Man, 9 Man, 1 Pin
        )
        assertFalse(AgariEvaluator.isAgari(hand), "Sequence wrapping suits should be invalid")
    }
}
