package com.dragoncowkarma.mahcalc

import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import com.dragoncowkarma.mahcalc.ui.MahjongScreenModel




interface AppComponent {
    val platform: Platform
    val tileDetectionModel: TileDetectionModel
    val mahjongScreenModel: MahjongScreenModel
}
