package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import com.kyant.pixelmusic.api.song.SongResult
import com.kyant.pixelmusic.locals.LocalJsonParser
import com.kyant.pixelmusic.util.launchedIOEffect
import java.net.URL

typealias SongId = Long

@Composable
fun SongId.findUrl(): String? {
    val jsonParser = LocalJsonParser.current
    return launchedIOEffect {
        jsonParser.parse<SongResult>(
            URL("$API2/song/url?id=${this@findUrl}").readText()
        )?.data?.get(0)?.url
    }
}