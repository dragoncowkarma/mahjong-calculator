package com.dragoncowkarma.mahcalc.models

import kotlin.jvm.JvmInline

enum class Suit {
    Man, Pin, Sou, Honor
}

/**
 * A Mahjong tile represented as an inline class to avoid object allocation overhead.
 *
 * There are 34 distinct tiles in Mahjong:
 * 0-8: Man (Characters) 1-9
 * 9-17: Pin (Circles) 1-9
 * 18-26: Sou (Bamboo) 1-9
 * 27-33: Honor (East, South, West, North, White, Green, Red)
 */
@JvmInline
value class MahjongTile(val id: Int) {
    init {
        require(id in 0..33) { "Tile id must be between 0 and 33, but was $id" }
    }

    val suit: Suit
        get() = when (id) {
            in 0..8 -> Suit.Man
            in 9..17 -> Suit.Pin
            in 18..26 -> Suit.Sou
            else -> Suit.Honor
        }

    /**
     * The number of the tile (1-9) for suited tiles, or an index (1-7) for honor tiles.
     */
    val number: Int
        get() = when (id) {
            in 0..26 -> (id % 9) + 1
            else -> id - 26
        }
}
