package com.kyant.pixelmusic.util

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kyant.pixelmusic.api.AlbumId
import com.kyant.pixelmusic.api.findAlbum
import com.kyant.pixelmusic.locals.LocalCacheDataStore
import com.kyant.pixelmusic.locals.ProvideCacheDataStore

val EmptyImage = ImageBitmap(1, 1)

@Composable
fun Any.loadImage(): ImageBitmap? {
    val context = LocalContext.current
    return launchedIOEffect {
        val imageLoader = ImageLoader.Builder(context)
            .build()
        val request = ImageRequest.Builder(context)
            .data(this@loadImage)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
        imageLoader.execute(request).drawable?.toBitmap()?.asImageBitmap()
    }
}

@Composable
fun AlbumId.loadCover(): ImageBitmap? {
    var cover by remember(this) { mutableStateOf<ImageBitmap?>(null) }
    var cached by remember(this) { mutableStateOf(false) }
    ProvideCacheDataStore("covers") {
        val dataStore = LocalCacheDataStore.current
        val path = "$this.jpg"
        if (dataStore.contains(path)) {
            cached = true
        } else {
            "${findAlbum()?.album?.picUrl}?param=500y500".toUri()
                .loadImage()
                ?.asAndroidBitmap()
                ?.let {
                    LaunchedIOEffectUnit {
                        dataStore.writeBitmap(path, it)
                        cached = true
                    }
                }
        }
        cached.LaunchedIOEffectUnit {
            if (cached) {
                cover = dataStore.getBitmap(path)?.asImageBitmap()
            }
        }
    }
    return cover
}