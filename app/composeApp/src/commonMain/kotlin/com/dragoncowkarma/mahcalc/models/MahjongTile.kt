package com.dragoncowkarma.mahcalc.models

enum class Suit {
    Man, Pin, Sou, Honor
}

/**
 * A Mahjong tile represented as a data class.
 *
 * There are 34 distinct tiles in Mahjong:
 * 0-8: Man (Characters) 1-9
 * 9-17: Pin (Circles) 1-9
 * 18-26: Sou (Bamboo) 1-9
 * 27-33: Honor (East, South, West, North, White, Green, Red)
 */
data class MahjongTile(val id: Int, val isAkadora: Boolean = false) {
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

    /**
     * Returns a human-readable Korean Mahjong string for this tile.
     *
     * Conversion rules:
     * - Manzu (만): 1만 ~ 9만
     * - Pinzu (통): 1통 ~ 9통
     * - Souzu (삭): 1삭 ~ 9삭
     * - Winds: 동(East), 남(South), 서(West), 북(North)
     * - Dragons: 백(White), 발(Green), 중(Red)
     * - Akadora modifier: appends (아카도라) e.g. "5통(아카도라)"
     */
    fun toKoreanString(): String {
        val base = when (id) {
            in 0..8 -> "${number}만"
            in 9..17 -> "${number}통"
            in 18..26 -> "${number}삭"
            27 -> "동"
            28 -> "남"
            29 -> "서"
            30 -> "북"
            31 -> "백"
            32 -> "발"
            33 -> "중"
            else -> "?"
        }
        return if (isAkadora) "$base(아카도라)" else base
    }
}

/**
 * Converts a list of [MahjongTile] to a comma-separated Korean display string.
 *
 * Example output: "2만, 2만, 2만, 3통, 4통, 5통(아카도라), 백, 백, 백, 남, 남, 남, 7삭, 7삭"
 */
fun List<MahjongTile>.toKoreanDisplayString(): String =
    joinToString(", ") { it.toKoreanString() }
