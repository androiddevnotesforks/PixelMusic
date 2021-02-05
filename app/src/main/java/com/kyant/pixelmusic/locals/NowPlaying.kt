package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.compositionLocalOf
import com.kyant.pixelmusic.media.Song

val LocalNowPlaying = compositionLocalOf { Song() }

@Composable
fun ProvideNowPlaying(song: Song?, content: @Composable () -> Unit) {
    if (song?.id != null) {
        Providers(LocalNowPlaying provides song, content = {
            ProvideLyrics(song) {
                ProvideAmplitudes(false) {
                    content()
                }
            }
        })
    }
}