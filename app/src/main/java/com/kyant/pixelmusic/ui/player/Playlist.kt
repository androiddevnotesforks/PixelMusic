package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.Media

@Composable
fun Playlist(modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxSize(),
        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        elevation = 8.dp
    ) {
        Column {
            Text(
                "Playlist",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.h5
            )
            LazyColumn {
                itemsIndexed(Media.songs) { index, song ->
                    PlaylistItem(index, song, song.id == LocalNowPlaying.current.id)
                }
            }
        }
    }
}