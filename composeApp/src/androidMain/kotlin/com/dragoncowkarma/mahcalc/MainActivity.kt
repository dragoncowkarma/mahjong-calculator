package com.dragoncowkarma.mahcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dragoncowkarma.mahcalc.ui.MahjongScreen
import cafe.adriel.voyager.navigator.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val component = AndroidAppComponent::class.create()

        setContent {
            Navigator(MahjongScreen(component))
        }
    }
}