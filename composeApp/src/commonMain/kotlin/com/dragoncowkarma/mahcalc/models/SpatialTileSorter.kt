package com.dragoncowkarma.mahcalc.models

object SpatialTileSorter {
    /**
     * Sorts a list of BoundingBoxes spatially: first grouping them by rows (Y-axis) with a tolerance,
     * then sorting left-to-right (X-axis) within each row.
     * Returns an IntArray containing up to 14 classIds representing the sorted tiles.
     */
    fun sortToTileIds(boxes: List<BoundingBox>): IntArray {
        if (boxes.isEmpty()) return IntArray(0)

        // Calculate average height to determine the row threshold
        val avgHeight = boxes.sumOf { it.height.toDouble() } / boxes.size
        val threshold = avgHeight * 0.5

        // Sort by Y-axis initially to start clustering
        val sortedByY = boxes.sortedBy { it.y }

        val clusters = mutableListOf<MutableList<BoundingBox>>()
        var currentCluster = mutableListOf(sortedByY.first())
        clusters.add(currentCluster)

        for (i in 1 until sortedByY.size) {
            val box = sortedByY[i]
            // If the difference in Y from the first element of the current cluster is within the threshold,
            // we consider it part of the same row.
            if (box.y - currentCluster.first().y < threshold) {
                currentCluster.add(box)
            } else {
                currentCluster = mutableListOf(box)
                clusters.add(currentCluster)
            }
        }

        // Sort each cluster by X-axis and collect classIds
        val sortedClassIds = clusters.flatMap { cluster ->
            cluster.sortedBy { it.x }.map { it.classId }
        }

        // Return exactly up to 14 classIds as IntArray
        val resultSize = minOf(14, sortedClassIds.size)
        val result = IntArray(resultSize)
        for (i in 0 until resultSize) {
            result[i] = sortedClassIds[i]
        }

        return result
    }
}
