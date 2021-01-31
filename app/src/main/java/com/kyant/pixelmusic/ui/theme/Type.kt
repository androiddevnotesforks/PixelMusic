package com.kyant.pixelmusic.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kyant.pixelmusic.R

val typography = Typography(
    defaultFontFamily = FontFamily(Font(R.font.google_sans_rounded)),
    h5 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.25.sp
    )
)