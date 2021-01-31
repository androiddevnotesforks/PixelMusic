package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.compositionLocalOf
import com.kyant.pixelmusic.media.Song

val LocalNowPlaying = compositionLocalOf<Song> { error("No NowPlaying was provided.") }

@Composable
fun ProvideNowPlaying(song: Song?, content: @Composable () -> Unit) {
    if (song?.mediaUri != null) {
        Providers(LocalNowPlaying provides song, content = {
            ProvideLyrics(song) {
                ProvideAmplitudes(false) {
                    content()
                }
            }
        })
    }
}