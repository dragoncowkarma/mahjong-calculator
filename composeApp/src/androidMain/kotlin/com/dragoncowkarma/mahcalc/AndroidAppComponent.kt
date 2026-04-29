package com.dragoncowkarma.mahcalc

import com.dragoncowkarma.mahcalc.models.AndroidImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.AndroidImagePicker
import com.dragoncowkarma.mahcalc.models.AndroidTileDetectionModel
import com.dragoncowkarma.mahcalc.models.ImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.ImagePicker
import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class AndroidAppComponent : AppComponent {
    @Provides
    fun providePlatform(): Platform = AndroidPlatform()

    @Provides
    fun provideTileDetectionModel(): TileDetectionModel = AndroidTileDetectionModel()

    @Provides
    fun provideImagePicker(): ImagePicker = AndroidImagePicker()

    @Provides
    fun provideImageBitmapDecoder(): ImageBitmapDecoder = AndroidImageBitmapDecoder()
}
