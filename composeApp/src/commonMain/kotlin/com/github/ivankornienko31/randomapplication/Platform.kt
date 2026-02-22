package com.github.ivankornienko31.randomapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform