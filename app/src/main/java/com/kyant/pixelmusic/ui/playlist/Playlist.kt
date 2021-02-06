package com.kyant.pixelmusic.ui.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findPlaylist
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.song.Song

@Composable
fun Playlist(
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val playlist = topList.value?.id?.findPlaylist()
    Column(modifier) {
        topList.value?.name?.let {
            Text(
                it,
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.h5
            )
        }
        LazyColumn {
            playlist?.playlist?.tracks?.let { tracks ->
                items(tracks, { it.id.toString() }) {
                    Song(it.toSong())
                }
            }
        }
    }
}