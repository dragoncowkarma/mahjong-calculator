package com.dragoncowkarma.mahcalc

import androidx.compose.runtime.staticCompositionLocalOf
import com.dragoncowkarma.mahcalc.models.ImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.ImagePicker
import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import com.dragoncowkarma.mahcalc.ui.MahjongScreenModel
import com.dragoncowkarma.mahcalc.ui.TileRecognitionScreenModel
import com.dragoncowkarma.mahcalc.ui.YakuCalculationScreenModel

interface AppComponent {
    val platform: Platform
    val tileDetectionModel: TileDetectionModel
    val imagePicker: ImagePicker
    val imageBitmapDecoder: ImageBitmapDecoder
    val mahjongScreenModel: MahjongScreenModel
    val tileRecognitionScreenModel: TileRecognitionScreenModel
    val yakuCalculationScreenModel: YakuCalculationScreenModel
}

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("No AppComponent provided")
}
