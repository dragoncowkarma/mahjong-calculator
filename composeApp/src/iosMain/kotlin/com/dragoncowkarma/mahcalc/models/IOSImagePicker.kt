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
    override fun registerPicker(onImagePicked: (ByteArray?) -> Unit): (ImageSource) -> Unit {
        val delegate = remember {
            object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                    if (image != null) {
                        val imageData = UIImageJPEGRepresentation(image, 0.8)
                        if (imageData != null) {
                            val bytes = ByteArray(imageData.length.toInt())
                            imageData.getBytes(bytes.refTo(0), imageData.length)
                            onImagePicked(bytes)
                        } else {
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

        return { source ->
            val pickerController = UIImagePickerController()
            pickerController.delegate = delegate
            pickerController.sourceType = when (source) {
                ImageSource.GALLERY -> UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
                ImageSource.CAMERA -> UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            }
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                pickerController,
                animated = true,
                completion = null
            )
        }
    }
}
