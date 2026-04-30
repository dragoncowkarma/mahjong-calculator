package com.dragoncowkarma.mahcalc

import com.dragoncowkarma.mahcalc.create
import com.dragoncowkarma.mahcalc.models.IOSImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.IOSImagePicker
import com.dragoncowkarma.mahcalc.models.IOSTileDetectionModel
import com.dragoncowkarma.mahcalc.models.ImageBitmapDecoder
import com.dragoncowkarma.mahcalc.models.ImagePicker
import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class IosAppComponent : AppComponent {
    @Provides
    fun providePlatform(): Platform = IOSPlatform()

    @Provides
    fun provideTileDetectionModel(): TileDetectionModel = IOSTileDetectionModel()

    @Provides
    fun provideImagePicker(): ImagePicker = IOSImagePicker()

    @Provides
    fun provideImageBitmapDecoder(): ImageBitmapDecoder = IOSImageBitmapDecoder()
}

fun createIosComponent(): IosAppComponent = IosAppComponent::class.create()
