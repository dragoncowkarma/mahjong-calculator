package com.dragoncowkarma.mahcalc.models

/**
 * iOS implementation of the TileDetectionModel.
 * Intended to be backed by CoreML or TFLite iOS SDK.
 */
actual class TileDetectionModel {
    actual fun detect(frameData: ByteArray, width: Int, height: Int): List<BoundingBox> {
        // TODO: Implement actual object detection logic using CoreML or TFLite iOS SDK.
        // For now, return mock data since model files are unavailable.
        return MockDataGenerator.mockBoundingBoxes
    }
}
