package com.dragoncowkarma.mahcalc.calculator

/**
 * Represents a single meld or a pair in a decomposed hand.
 *
 * @param type 0 for Pair, 1 for Sequence (Shuntsu), 2 for Triplet (Koutsu)
 * @param tiles An array containing the IDs of the tiles in the meld
 */
data class Meld(val type: Int, val tiles: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Meld

        if (type != other.type) return false
        if (!tiles.contentEquals(other.tiles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + tiles.contentHashCode()
        return result
    }
}
