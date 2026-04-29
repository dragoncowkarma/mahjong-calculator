package com.dragoncowkarma.mahcalc

import com.dragoncowkarma.mahcalc.models.IOSTileDetectionModel
import com.dragoncowkarma.mahcalc.models.TileDetectionModel
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class IosAppComponent : AppComponent {
    @Provides
    fun providePlatform(): Platform = IOSPlatform()

    @Provides
    fun provideTileDetectionModel(): TileDetectionModel = IOSTileDetectionModel()
}
