package com.dragoncowkarma.mahcalc.models

import androidx.compose.runtime.Composable

interface ImagePicker {
    @Composable
    fun registerPicker(onImagePicked: (ByteArray?) -> Unit): () -> Unit
}
