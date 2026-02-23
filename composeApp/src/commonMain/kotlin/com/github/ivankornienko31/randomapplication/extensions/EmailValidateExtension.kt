package com.github.ivankornienko31.randomapplication.extensions

fun String.isValidEmail(): Boolean {
    if (isEmpty()) return false

    return Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches(this)
}