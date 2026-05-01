package com.dragoncowkarma.mahcalc.ui

import com.dragoncowkarma.mahcalc.models.MahjongTile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class YakuCalculationScreenModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testAddAndRemoveTile() = runTest {
        val model = YakuCalculationScreenModel()
        assertEquals(0, model.selectedTiles.value.size)

        // Add tile
        model.addTile(MahjongTile(0)) // 1 Man
        assertEquals(1, model.selectedTiles.value.size)
        assertEquals(0, model.selectedTiles.value[0].id)

        // Remove tile
        model.removeTile(0)
        assertEquals(0, model.selectedTiles.value.size)
    }

    @Test
    fun testMaxTilesConstraint() = runTest {
        val model = YakuCalculationScreenModel()
        // Add 18 tiles
        for (i in 0 until 18) {
            model.addTile(MahjongTile(i % 34))
        }
        assertEquals(18, model.selectedTiles.value.size)

        // Try to add 19th tile
        model.addTile(MahjongTile(33))
        assertEquals(18, model.selectedTiles.value.size, "Should not exceed 18 tiles")
    }

    @Test
    fun testMaxFourSameTiles() = runTest {
        val model = YakuCalculationScreenModel()
        // Add four 1 Man
        model.addTile(MahjongTile(0))
        model.addTile(MahjongTile(0))
        model.addTile(MahjongTile(0))
        model.addTile(MahjongTile(0))
        assertEquals(4, model.selectedTiles.value.size)

        // Try to add 5th 1 Man
        model.addTile(MahjongTile(0))
        assertEquals(4, model.selectedTiles.value.size, "Should not allow more than 4 of the same tile")
    }

    @Test
    fun testCalculationWithInvalidHand() = runTest {
        val model = YakuCalculationScreenModel()
        // Add 14 random tiles (not a winning hand)
        for (i in 0 until 14) {
            model.addTile(MahjongTile(i))
        }

        model.calculate()
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to finish

        assertNull(model.resultState.value)
        assertNotNull(model.validationError.value)
        assertEquals("Not a valid winning hand (Agari).", model.validationError.value)
    }
}
