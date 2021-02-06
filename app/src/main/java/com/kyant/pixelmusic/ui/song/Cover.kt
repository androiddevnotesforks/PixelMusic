package com.kyant.pixelmusic.ui.song

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.util.EmptyImage

@Composable
fun Cover(
    song: Song,
    modifier: Modifier = Modifier
) {
    val alpha = remember(song.albumId) { Animatable(0f) }.apply {
        LaunchedEffect(song.icon == null) {
            if (song.icon != null) {
                animateTo(1f)
            } else {
                animateTo(0f)
            }
        }
    }
    Image(
        song.icon ?: EmptyImage,
        song.title.toString(),
        modifier.alpha(alpha.value)
    )
}