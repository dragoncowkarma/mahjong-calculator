package com.dragoncowkarma.mahcalc.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.UIKit.*
import platform.Foundation.*
import platform.darwin.NSObject

class IOSImagePicker : ImagePicker {
    @OptIn(ExperimentalForeignApi::class)
    @Composable
    override fun registerPicker(onImagePicked: (ByteArray?) -> Unit): () -> Unit {
        val delegate = remember {
            object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                    if (image != null) {
                        try {
                            val imageData = UIImageJPEGRepresentation(image, 0.8)
                            if (imageData != null) {
                                val bytes = ByteArray(imageData.length.toInt())
                                imageData.getBytes(bytes.refTo(0), imageData.length)
                                onImagePicked(bytes)
                            } else {
                                onImagePicked(null)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            onImagePicked(null)
                        }
                    } else {
                        onImagePicked(null)
                    }
                    picker.dismissViewControllerAnimated(true, null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    onImagePicked(null)
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
        }

        return {
            val alertController = UIAlertController.alertControllerWithTitle(
                title = "Select Image Source",
                message = null,
                preferredStyle = UIAlertControllerStyleActionSheet
            )

            val galleryAction = UIAlertAction.actionWithTitle(
                title = "Gallery",
                style = UIAlertActionStyleDefault,
                handler = { _ ->
                    val pickerController = UIImagePickerController()
                    pickerController.delegate = delegate
                    pickerController.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
                    try {
                        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                            pickerController,
                            animated = true,
                            completion = null
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )

            val cameraAction = UIAlertAction.actionWithTitle(
                title = "Camera",
                style = UIAlertActionStyleDefault,
                handler = { _ ->
                    val pickerController = UIImagePickerController()
                    pickerController.delegate = delegate
                    pickerController.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                    try {
                        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                            pickerController,
                            animated = true,
                            completion = null
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )

            val cancelAction = UIAlertAction.actionWithTitle(
                title = "Cancel",
                style = UIAlertActionStyleCancel,
                handler = null
            )

            alertController.addAction(galleryAction)
            alertController.addAction(cameraAction)
            alertController.addAction(cancelAction)

            try {
                UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                    alertController,
                    animated = true,
                    completion = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
