package com.kyant.pixelmusic.util

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Duration.toPositiveTimeString(): String {
    return toString().dropWhile { !it.isDigit() }
}

@OptIn(ExperimentalTime::class)
fun Duration.toOffsetTimeString(): String {
    return "${if (inNanoseconds >= 0) "+" else "-"} ${toPositiveTimeString()}"
}