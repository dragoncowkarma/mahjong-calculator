package com.dragoncowkarma.mahcalc

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator


fun MainViewController() = ComposeUIViewController {
    val component = androidx.compose.runtime.remember { createIosComponent() }
    androidx.compose.runtime.CompositionLocalProvider(LocalAppComponent provides component) {
        Navigator(MahjongScreen())
    }
}