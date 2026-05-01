package com.dragoncowkarma.mahcalc.models

data class BoundingBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val confidence: Float,
    val classId: Int
)
