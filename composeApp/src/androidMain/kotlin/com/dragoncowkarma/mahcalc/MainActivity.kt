package com.dragoncowkarma.mahcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val component = androidx.compose.runtime.remember { AndroidAppComponent::class.create() }
            androidx.compose.runtime.CompositionLocalProvider(LocalAppComponent provides component) {
                Navigator(MahjongScreen())
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val component = AndroidAppComponent::class.create()
    androidx.compose.runtime.CompositionLocalProvider(LocalAppComponent provides component) {
        Navigator(MahjongScreen())
    }
}