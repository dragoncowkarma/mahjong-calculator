package com.dragoncowkarma.mahcalc.models

import kotlin.math.max
import kotlin.math.min

/**
 * Calculates the Intersection over Union (IoU) of two bounding boxes.
 */
fun calculateIoU(box1: BoundingBox, box2: BoundingBox): Float {
    val x1 = max(box1.x, box2.x)
    val y1 = max(box1.y, box2.y)
    val x2 = min(box1.x + box1.width, box2.x + box2.width)
    val y2 = min(box1.y + box1.height, box2.y + box2.height)

    val intersectionArea = max(0f, x2 - x1) * max(0f, y2 - y1)

    val box1Area = box1.width * box1.height
    val box2Area = box2.width * box2.height

    val unionArea = box1Area + box2Area - intersectionArea

    return if (unionArea > 0) intersectionArea / unionArea else 0f
}

/**
 * Performs Non-Maximum Suppression (NMS) to filter out overlapping bounding boxes.
 *
 * @param boxes The list of bounding boxes to filter.
 * @param iouThreshold The threshold above which boxes are considered to be overlapping.
 * @return A filtered list of bounding boxes.
 */
fun nonMaximumSuppression(boxes: List<BoundingBox>, iouThreshold: Float): List<BoundingBox> {
    if (boxes.isEmpty()) return emptyList()

    // Sort boxes by confidence in descending order
    val sortedBoxes = boxes.sortedByDescending { it.confidence }.toMutableList()
    val selectedBoxes = mutableListOf<BoundingBox>()

    while (sortedBoxes.isNotEmpty()) {
        val currentBox = sortedBoxes.removeAt(0)
        selectedBoxes.add(currentBox)

        val iterator = sortedBoxes.iterator()
        while (iterator.hasNext()) {
            val nextBox = iterator.next()
            // Only compare boxes of the same class
            if (currentBox.classId == nextBox.classId) {
                val iou = calculateIoU(currentBox, nextBox)
                if (iou > iouThreshold) {
                    iterator.remove()
                }
            }
        }
    }

    return selectedBoxes
}
