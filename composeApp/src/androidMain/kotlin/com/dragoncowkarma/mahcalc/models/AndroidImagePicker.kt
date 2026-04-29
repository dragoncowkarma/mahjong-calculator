package com.dragoncowkarma.mahcalc.models

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

import android.app.AlertDialog

class AndroidImagePicker : ImagePicker {
    @Composable
    override fun registerPicker(onImagePicked: (ByteArray?) -> Unit): () -> Unit {
        val context = LocalContext.current
        val contentResolver = remember { context.contentResolver }

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                val inputStream = contentResolver.openInputStream(uri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                onImagePicked(bytes)
            } else {
                onImagePicked(null)
            }
        }

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                val stream = java.io.ByteArrayOutputStream()
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, stream)
                val bytes = stream.toByteArray()
                onImagePicked(bytes)
            } else {
                onImagePicked(null)
            }
        }

        return {
            val options = arrayOf("Gallery", "Camera")
            AlertDialog.Builder(context)
                .setTitle("Select Image Source")
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> galleryLauncher.launch("image/*")
                        1 -> cameraLauncher.launch(null)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
