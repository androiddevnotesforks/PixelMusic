package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.kyant.pixelmusic.media.Song

val LocalNowPlaying = compositionLocalOf { Song() }

@Composable
fun ProvideNowPlaying(song: Song?, content: @Composable () -> Unit) {
    if (song?.id != null) {
        CompositionLocalProvider(LocalNowPlaying provides song, content = content)
    }
}