package com.kyant.pixelmusic.ui.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.Media

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerPlaylist(
    visible: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible.value,
        modifier,
        enter = slideInVertically({ with(density) { -(it + 24.dp.roundToPx()) } }),
        exit = slideOutVertically({ with(density) { -(it + 24.dp.roundToPx()) } })
    ) {
        Card(
            Modifier.fillMaxSize(),
            RoundedCornerShape(0.dp),
            elevation = 24.dp
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton({ visible.value = false }) {
                        Icon(Icons.Outlined.Close, "Close")
                    }
                    Text(
                        "Playlist",
                        Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                LazyColumn {
                    itemsIndexed(Media.songs) { index, song ->
                        PlayerPlaylistItem(index, song, song.id == LocalNowPlaying.current.id)
                    }
                }
            }
        }
    }
}