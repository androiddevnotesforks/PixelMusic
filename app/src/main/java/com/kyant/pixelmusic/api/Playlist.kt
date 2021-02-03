package com.kyant.pixelmusic.api

import androidx.compose.runtime.Composable
import com.kyant.pixelmusic.api.playlist.PlaylistResult
import com.kyant.pixelmusic.api.toplist.TopListResult
import com.kyant.pixelmusic.locals.LocalJsonParser
import com.kyant.pixelmusic.util.launchedIOEffect
import java.net.URL

@Composable
fun TopListId.findPlaylist(): PlaylistResult? {
    val jsonParser = LocalJsonParser.current
    return launchedIOEffect {
        jsonParser.parse<PlaylistResult>(
            URL("$API2/playlist/detail?id=${this@findPlaylist}").readText()
        )
    }
}