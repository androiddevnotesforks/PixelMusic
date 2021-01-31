package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.compositionLocalOf
import com.kyant.pixelmusic.api.EmptyLyrics
import com.kyant.pixelmusic.api.Lyrics
import com.kyant.pixelmusic.api.findLyrics
import com.kyant.pixelmusic.media.Song

val LocalLyrics = compositionLocalOf<Lyrics> { error("No Lyrics was provided.") }

@Composable
fun ProvideLyrics(song: Song, content: @Composable () -> Unit) {
    val lyrics = song.id?.findLyrics()
    Providers(LocalLyrics provides (lyrics ?: EmptyLyrics), content = content)
}