package com.dragoncowkarma.mahcalc.models

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class AndroidImageBitmapDecoder : ImageBitmapDecoder {
    override fun decode(byteArray: ByteArray): ImageBitmap? {
        return try {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }
}
