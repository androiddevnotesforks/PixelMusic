package com.kyant.pixelmusic.api

import androidx.compose.runtime.Composable
import com.kyant.pixelmusic.api.album.AlbumResult
import com.kyant.pixelmusic.locals.LocalJsonParser
import com.kyant.pixelmusic.util.launchedIOEffect
import java.net.URL

typealias AlbumId = Long

@Composable
fun AlbumId.findAlbum(): AlbumResult? {
    val jsonParser = LocalJsonParser.current
    return launchedIOEffect {
        jsonParser.parse<AlbumResult>(URL("$API2/album?id=${this@findAlbum}").readText())
    }
}