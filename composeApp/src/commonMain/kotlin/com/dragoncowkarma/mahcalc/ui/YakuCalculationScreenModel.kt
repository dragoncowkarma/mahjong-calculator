package com.dragoncowkarma.mahcalc.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.dragoncowkarma.mahcalc.calculator.MahjongCalculator
import com.dragoncowkarma.mahcalc.calculator.ScoreCalculator
import com.dragoncowkarma.mahcalc.models.AgariEvaluator
import com.dragoncowkarma.mahcalc.models.MahjongTile
import com.dragoncowkarma.mahcalc.models.MatchContext
import com.dragoncowkarma.mahcalc.models.ScoreResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class YakuCalculationScreenModel : ScreenModel {
    private val _selectedTiles = MutableStateFlow<List<MahjongTile>>(emptyList())
    val selectedTiles: StateFlow<List<MahjongTile>> = _selectedTiles.asStateFlow()

    private val _resultState = MutableStateFlow<ScoreResult?>(null)
    val resultState: StateFlow<ScoreResult?> = _resultState.asStateFlow()

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    private val _matchContext = MutableStateFlow(MatchContext())

    fun addTile(tile: MahjongTile) {
        val currentTiles = _selectedTiles.value
        // Max tiles limit is 18 (e.g. 4 kans + 1 pair)
        if (currentTiles.size < 18) {
            val count = currentTiles.count { it.id == tile.id }
            if (count < 4) {
                _selectedTiles.value = currentTiles + tile
                validateTiles()
            }
        }
    }

    fun removeTile(index: Int) {
        val currentTiles = _selectedTiles.value
        if (index in currentTiles.indices) {
            val newTiles = currentTiles.toMutableList()
            newTiles.removeAt(index)
            _selectedTiles.value = newTiles
            validateTiles()
        }
    }

    fun clearTiles() {
        _selectedTiles.value = emptyList()
        _resultState.value = null
        _validationError.value = null
    }

    private fun validateTiles() {
        val size = _selectedTiles.value.size
        _resultState.value = null
        if (size in 14..18) {
            _validationError.value = null
        } else {
            _validationError.value = "Selected tiles must be between 14 and 18."
        }
    }

    fun calculate() {
        val currentTiles = _selectedTiles.value
        if (currentTiles.size !in 14..18) {
            _validationError.value = "Selected tiles must be between 14 and 18."
            return
        }

        screenModelScope.launch {
            val hand = currentTiles.map { it.id }.toIntArray()
            val context = _matchContext.value
            // Treat the last tile as winning tile for simplicity.
            val winningTile = hand.last()

            // To support 14-18 tiles we would normally have logic to collapse kans.
            // But AgariEvaluator and MahjongCalculator currently assume 14 tiles hand length.
            // If length is exactly 14, we can compute. Otherwise, it might need to extract kans.
            // For now, if length is exactly 14, compute.
            if (hand.size == 14) {
                val isAgari = AgariEvaluator.isAgari(hand)
                if (isAgari) {
                    val (yaku, fu) = MahjongCalculator.calculate(hand, winningTile, context)
                    if (yaku.isNotEmpty()) {
                        val han = calculateHanFromYakuNames(yaku, context.doraCount)
                        val scoreResult = ScoreCalculator.calculateScore(han, fu, context, yaku)
                        _resultState.value = scoreResult
                    } else {
                        _resultState.value = null
                        _validationError.value = "Valid Agari, but no Yaku found."
                    }
                } else {
                    _resultState.value = null
                    _validationError.value = "Not a valid winning hand (Agari)."
                }
            } else {
                // If more than 14 tiles, we need to adapt AgariEvaluator to handle melds correctly.
                // Assuming currently 14 tiles is only supported by engine.
                 _validationError.value = "Calculation for > 14 tiles (with Kan) is not fully supported yet by engine."
            }
        }
    }

    private fun calculateHanFromYakuNames(yakuList: List<String>, doraCount: Int): Int {
        var han = 0
        val twoHanYaku = setOf("Toitoi", "Sanankou", "Sankantsu", "Shousangen", "Honrouto", "Chanta", "Ittsu", "Sanshoku Doujun", "Sanshoku Doukou", "Chiitoitsu")
        val threeHanYaku = setOf("Honitsu", "Junchan", "Ryanpeikou")
        val sixHanYaku = setOf("Chinitsu")
        val yakuman = setOf("Kokushi Musou", "Suuankou", "Daisangen", "Shousuushii", "Daisuushii", "Tsuuiisou", "Chinrouto", "Ryuuiisou", "Kyuuren Poutou", "Sukantsu", "Tenhou", "Chiihou")

        for (yaku in yakuList) {
            if (yakuman.contains(yaku)) return 13
            han += when (yaku) {
                in twoHanYaku -> 2
                in threeHanYaku -> 3
                in sixHanYaku -> 6
                else -> 1
            }
        }

        return han + doraCount
    }
}
