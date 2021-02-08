package com.kyant.inimate.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class SuperellipseCornerShape(private var cornerSize: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val (width, height) = size
            val rx = (with(density) { cornerSize.toPx() } / (width / 2)).toDouble() * 100
            val ry = (with(density) { cornerSize.toPx() } / (height / 2)).toDouble() * 100
            for (i in 0 until 360) {
                val angle = i * 2 * Math.PI / 360.0
                val x = abs(cos(angle)).pow(rx / 100) * 50 *
                        abs(cos(angle) + 0.000_000_000_1) /
                        (cos(angle) + 0.000_000_000_1) + 50
                val y = abs(sin(angle)).pow(ry / 100) * 50 *
                        abs(sin(angle) + 0.000_000_000_1) /
                        (sin(angle) + 0.000_000_000_1) + 50
                if (i == 0) moveTo((x / 100 * width).toFloat(), (y / 100 * height).toFloat())
                else lineTo((x / 100 * width).toFloat(), (y / 100 * height).toFloat())
            }
            close()
        }
        return Outline.Generic(path)
    }
}