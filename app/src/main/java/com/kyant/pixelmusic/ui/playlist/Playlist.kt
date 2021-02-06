package com.kyant.pixelmusic.ui.playlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
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
    topList.value?.let { TopListCover(it) }
    LazyColumn(modifier) {
        playlist?.playlist?.tracks.let { tracks ->
            tracks?.let {
                items(tracks, { it.id.toString() }) {
                    Song(it.toSong())
                }
            }
        }
    }
}