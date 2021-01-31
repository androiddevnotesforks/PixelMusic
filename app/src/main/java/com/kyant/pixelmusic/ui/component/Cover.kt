package com.kyant.pixelmusic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.util.EmptyImage

@Composable
fun Cover(
    song: Song,
    modifier: Modifier = Modifier
) {
    Image(
        song.icon?.asImageBitmap() ?: EmptyImage,
        song.title.toString(),
        modifier
    )
}