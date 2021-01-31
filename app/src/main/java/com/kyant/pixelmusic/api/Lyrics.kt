package com.kyant.pixelmusic.api

import androidx.compose.runtime.Composable
import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.lyrics.LyricResult
import com.kyant.pixelmusic.media.SongId
import com.kyant.pixelmusic.util.launchedIOEffect
import com.kyant.pixelmusic.util.toLyrics
import java.net.URL

typealias Time = String
typealias Content = String
typealias Lyrics = Map<Time, Content>

val EmptyLyrics: Lyrics = emptyMap()

@Composable
fun SongId.findLyrics(): Lyrics? = launchedIOEffect {
    Klaxon().parse<LyricResult>(URL("$API?type=lyric&id=${this@findLyrics}").readText())
        ?.lrc?.lyric?.toLyrics()
}