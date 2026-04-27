package com.dragoncowkarma.mahcalc.models

/**
 * Android implementation of the TileDetectionModel.
 * Intended to be backed by TensorFlow Lite C-API or Google ML Kit Object Detection.
 */
actual class TileDetectionModel {
    actual fun detect(frameData: ByteArray, width: Int, height: Int): List<BoundingBox> {
        // TODO: Implement actual object detection logic using TFLite or ML Kit.
        // For now, return mock data since model files are unavailable.
        return MockDataGenerator.mockBoundingBoxes
    }
}
