package com.kyant.pixelmusic.ui.playlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.kyant.inimate.layer.ForeLayer
import com.kyant.pixelmusic.api.findPlaylist
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.component.Song

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Playlist(
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val playlist = topList.value?.id?.findPlaylist()
    ForeLayer(rememberSwipeableState(topList.value != null), modifier) {
        IconButton({ topList.value = null }) {
            Icon(Icons.Outlined.Close, "Close")
        }
        topList.value?.let { TopListCover(it) }
        LazyColumn {
            playlist?.playlist?.tracks.let { tracks ->
                tracks?.let {
                    items(tracks) {
                        Song(it.toSong())
                    }
                }
            }
        }
    }
}