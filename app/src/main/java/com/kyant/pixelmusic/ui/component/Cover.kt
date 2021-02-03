package com.kyant.pixelmusic.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.LaunchedIOEffectUnit

@Composable
fun Cover(
    song: Song,
    modifier: Modifier = Modifier
) {
    val alpha = remember(song.icon) { Animatable(0f) }
    song.icon?.LaunchedIOEffectUnit {
        alpha.animateTo(1f)
    }
    Image(
        song.icon?.asImageBitmap() ?: EmptyImage,
        song.title.toString(),
        modifier.alpha(alpha.value)
    )
}