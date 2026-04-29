package com.dragoncowkarma.mahcalc

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator


fun MainViewController() = ComposeUIViewController {
    val component = createIosComponent()
    Navigator(MahjongScreen(component))
}