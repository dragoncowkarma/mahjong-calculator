package com.dragoncowkarma.mahcalc.models

/**
 * Platform-specific on-device Object Detection inference engine.
 * Takes camera frame bytes and outputs a list of bounding boxes.
 */
expect class TileDetectionModel() {
    fun detect(frameData: ByteArray, width: Int, height: Int): List<BoundingBox>
}
