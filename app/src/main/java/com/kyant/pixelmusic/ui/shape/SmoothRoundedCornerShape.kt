package com.kyant.pixelmusic.ui.shape

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

class SmoothRoundedCornerShape(private val radius: Double = 5.0) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val m = radius.coerceIn(0.000_000_000_01..100.0)
            val r = minOf(size.width, size.height).toDouble() / 2
            val w = size.width.toDouble() / 2
            val h = size.height.toDouble() / 2
            for (i in 0 until (2 * r + 1).toInt()) {
                val x = (i - r) + w
                val y = abs(r.pow(m) - abs(i - r).pow(m)).pow(1 / m) + h
                if (i == 0) moveTo(x.toFloat(), y.toFloat())
                else lineTo(x.toFloat(), y.toFloat())
            }
            for (i in 2 * r.toInt() until (4 * r + 1).toInt()) {
                val x = (3 * r - i) + w
                val y = -abs(r.pow(m) - abs(3 * r - i).pow(m)).pow(1 / m) + h
                lineTo(x.toFloat(), y.toFloat())
            }
            close()
        }
        return Outline.Generic(path)
    }
}

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