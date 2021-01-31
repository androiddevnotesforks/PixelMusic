package com.kyant.pixelmusic.util

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest

val EmptyImage = ImageBitmap(1, 1)

@Composable
fun Any.loadImage(): ImageBitmap? {
    val context = LocalContext.current
    return launchedIOEffect {
        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build()
        val request = ImageRequest.Builder(context)
            .data(this@loadImage)
            .build()
        imageLoader.execute(request).drawable?.toBitmap()?.asImageBitmap()
    }
}