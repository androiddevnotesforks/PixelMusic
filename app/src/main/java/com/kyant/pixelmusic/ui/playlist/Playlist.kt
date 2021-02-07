package com.kyant.pixelmusic.ui.playlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlaylistPlay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findPlaylist
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.media.toSongs
import com.kyant.pixelmusic.ui.song.Song

@Composable
fun Playlist(
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val songs = topList.value?.id?.findPlaylist()?.playlist?.tracks?.toSongs()
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            topList.value?.name?.let {
                Text(
                    it,
                    Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h5
                )
            }
            OutlinedButton(
                {},
                Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(Icons.Outlined.PlaylistPlay, "Play all")
                Spacer(Modifier.preferredWidth(8.dp))
                Text("Play all")
            }
        }
        LazyColumn {
            if (!songs.isNullOrEmpty()) {
                items(songs, { it.id.toString() }) {
                    Song(it)
                }
            }
        }
    }
}