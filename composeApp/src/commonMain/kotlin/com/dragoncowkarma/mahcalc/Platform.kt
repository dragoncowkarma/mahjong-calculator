package com.dragoncowkarma.mahcalc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform