package com.kyant.pixelmusic.ui.playlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findPlaylist
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.component.Song

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Playlist(
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val playlist = topList.value?.id?.findPlaylist()
    AnimatedVisibility(
        topList.value != null,
        modifier,
        enter = slideInVertically({ with(density) { it + 24.dp.roundToPx() } }),
        exit = slideOutVertically({ with(density) { it + 24.dp.roundToPx() } })
    ) {
        Card(
            Modifier.fillMaxSize(),
            RoundedCornerShape(0.dp),
            elevation = 24.dp
        ) {
            Column {
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
    }
}