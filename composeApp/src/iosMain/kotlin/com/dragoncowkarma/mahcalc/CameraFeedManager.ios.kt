package com.dragoncowkarma.mahcalc

import platform.AVFoundation.*
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue
import platform.CoreMedia.*
import platform.CoreVideo.*
import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
class MyDelegate : NSObject(), AVCaptureVideoDataOutputSampleBufferDelegateProtocol {
    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputSampleBuffer: CMSampleBufferRef?,
        fromConnection: AVCaptureConnection
    ) {
        val imageBuffer = CMSampleBufferGetImageBuffer(didOutputSampleBuffer)
        if (imageBuffer != null) {
            CVPixelBufferLockBaseAddress(imageBuffer, 0u)
            val baseAddress = CVPixelBufferGetBaseAddress(imageBuffer)
            val bytesPerRow = CVPixelBufferGetBytesPerRow(imageBuffer)
            val height = CVPixelBufferGetHeight(imageBuffer)
            val totalBytes = (bytesPerRow * height).toInt()

            // Just verifying APIs
            if (baseAddress != null) {
                val ptr = baseAddress.reinterpret<ByteVar>()
                val bytes = ptr.readBytes(totalBytes)
            }
            CVPixelBufferUnlockBaseAddress(imageBuffer, 0u)
        }
    }
}
