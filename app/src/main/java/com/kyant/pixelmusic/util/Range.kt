package com.kyant.pixelmusic.util

operator fun ClosedFloatingPointRange<Float>.minus(float: Float): ClosedFloatingPointRange<Float> {
    return start - float..endInclusive - float
}

operator fun ClosedFloatingPointRange<Float>.times(float: Float): ClosedFloatingPointRange<Float> {
    return start * float..endInclusive * float
}