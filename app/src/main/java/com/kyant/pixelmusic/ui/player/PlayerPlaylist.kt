package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.inimate.layer.ForeLayer
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.Media

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerPlaylist(
    visible: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    ForeLayer(visible, modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton({ visible.animateTo(false) }) {
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