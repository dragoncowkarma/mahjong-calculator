package com.dragoncowkarma.mahcalc

import androidx.compose.runtime.staticCompositionLocalOf
import com.dragoncowkarma.mahcalc.models.ImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.ImagePicker
import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import com.dragoncowkarma.mahcalc.ui.MahjongScreenModel
import com.dragoncowkarma.mahcalc.ui.TileRecognitionScreenModel

interface AppComponent {
    val platform: Platform
    val tileDetectionModel: TileDetectionModel
    val imagePicker: ImagePicker
    val imageBitmapDecoder: ImageBitmapDecoder
    val mahjongScreenModel: MahjongScreenModel
    val tileRecognitionScreenModel: TileRecognitionScreenModel
}

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("No AppComponent provided")
}
