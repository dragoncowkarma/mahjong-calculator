package com.dragoncowkarma.mahcalc.models

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

class IOSImageBitmapDecoder : ImageBitmapDecoder {
    override fun decode(byteArray: ByteArray): ImageBitmap? {
        return try {
            val skiaImage = Image.makeFromEncoded(byteArray)
            skiaImage.toComposeImageBitmap()
        } catch (e: Exception) {
            null
        }
    }
}
