package com.dragoncowkarma.mahcalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.dragoncowkarma.mahcalc.models.MahjongTile

/**
 * Returns the Unicode character for a given Mahjong tile ID.
 */
fun getMahjongTileString(id: Int): String {
    val codePoint = when (id) {
        in 0..8 -> 0x1F007 + id // Man
        in 9..17 -> 0x1F019 + (id - 9) // Pin
        in 18..26 -> 0x1F010 + (id - 18) // Sou
        27 -> 0x1F000 // East
        28 -> 0x1F001 // South
        29 -> 0x1F002 // West
        30 -> 0x1F003 // North
        31 -> 0x1F006 // White
        32 -> 0x1F005 // Green
        33 -> 0x1F004 // Red
        else -> 0x1F02B // Back
    }

    return buildString {
        if (codePoint <= 0xFFFF) {
            append(codePoint.toChar())
        } else {
            val offset = codePoint - 0x10000
            val high = ((offset ushr 10) + 0xD800).toChar()
            val low = ((offset and 0x3FF) + 0xDC00).toChar()
            append(high)
            append(low)
        }
    }
}

@Composable
fun TileCorrectionPanel(
    tiles: List<MahjongTile>,
    onTilesCorrected: (List<MahjongTile>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        Text("수정할 패를 선택하세요:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // Current Tiles
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(tiles) { index, tile ->
                TileView(
                    tile = tile,
                    isSelected = index == selectedIndex,
                    onClick = {
                        selectedIndex = if (selectedIndex == index) null else index
                    }
                )
            }
        }

        // Palette
        if (selectedIndex != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("교체할 패를 선택하세요:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                // Man
                TileRowPalette(startId = 0, count = 9, selectedIndex = selectedIndex!!, tiles = tiles, onTilesCorrected = onTilesCorrected) { selectedIndex = null }
                // Pin
                TileRowPalette(startId = 9, count = 9, selectedIndex = selectedIndex!!, tiles = tiles, onTilesCorrected = onTilesCorrected) { selectedIndex = null }
                // Sou
                TileRowPalette(startId = 18, count = 9, selectedIndex = selectedIndex!!, tiles = tiles, onTilesCorrected = onTilesCorrected) { selectedIndex = null }
                // Honor
                TileRowPalette(startId = 27, count = 7, selectedIndex = selectedIndex!!, tiles = tiles, onTilesCorrected = onTilesCorrected) { selectedIndex = null }
            }
        }
    }
}

@Composable
private fun TileRowPalette(
    startId: Int,
    count: Int,
    selectedIndex: Int,
    tiles: List<MahjongTile>,
    onTilesCorrected: (List<MahjongTile>) -> Unit,
    onPaletteClosed: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        for (i in 0 until count) {
            val tileId = startId + i
            val tile = MahjongTile(tileId)
            TileView(
                tile = tile,
                isSelected = false,
                onClick = {
                    val newTiles = tiles.toMutableList()
                    newTiles[selectedIndex] = tile
                    onTilesCorrected(newTiles)
                    onPaletteClosed()
                }
            )
        }
    }
}

@Composable
fun TileView(tile: MahjongTile, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = getMahjongTileString(tile.id),
            fontSize = 32.sp
        )
    }
}

@Composable
fun PreviewTileCorrectionPanel() {
    MaterialTheme {
        var tiles by remember { mutableStateOf(List(14) { MahjongTile(it) }) }
        TileCorrectionPanel(
            tiles = tiles,
            onTilesCorrected = { tiles = it }
        )
    }
}
