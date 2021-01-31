package com.kyant.pixelmusic.util

import kotlin.math.E
import kotlin.math.pow

/** [https://en.wikipedia.org/wiki/Logistic_function] */
fun Double.normalize(): Double {
    return this / (16 + E.pow(-this))
}