package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.Media
import com.kyant.inimate.layer.TopSheet

@Composable
fun PlayerPlaylist(
    visible: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    TopSheet(visible, modifier) {
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