package com.dragoncowkarma.mahcalc

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import com.dragoncowkarma.mahcalc.ui.MahjongScreen

fun MainViewController() = ComposeUIViewController {
    val component = IosAppComponent::class.create()
    Navigator(MahjongScreen(component))
}