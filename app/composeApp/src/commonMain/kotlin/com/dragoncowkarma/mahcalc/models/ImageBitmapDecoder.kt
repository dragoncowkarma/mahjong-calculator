package com.dragoncowkarma.mahcalc.models

import androidx.compose.ui.graphics.ImageBitmap

interface ImageBitmapDecoder {
    fun decode(byteArray: ByteArray): ImageBitmap?
}
