package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.inimate.layer.ForeLayer
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.Media

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerPlaylist(
    state: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    ForeLayer(
        state,
        modifier
    ) {
        Text(
            "Playlist",
            Modifier.padding(16.dp),
            style = MaterialTheme.typography.h5
        )
        LazyColumn {
            itemsIndexed(Media.songs) { index, song ->
                PlayerPlaylistItem(index, song, song.id == LocalNowPlaying.current.id)
            }
        }
    }
}