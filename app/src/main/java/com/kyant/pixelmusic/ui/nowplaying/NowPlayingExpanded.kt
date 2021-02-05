package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FeaturedPlayList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.ui.component.ChipGroup
import com.kyant.pixelmusic.ui.component.StatefulProgressIndicator
import com.kyant.pixelmusic.ui.player.PlayController

@Composable
fun NowPlayingExpanded(
    modifier: Modifier = Modifier,
    contentState: NowPlayingContentState,
    onPlaylistButtonClick: () -> Unit,
    onTabClick: (NowPlayingContentState) -> Unit
) {
    val song = LocalNowPlaying.current
    val items = listOf(
        Triple(NowPlayingContentState.SONG.name, "Song", Icons.Outlined.Audiotrack),
        Triple(NowPlayingContentState.LYRICS.name, "Lyrics", Icons.Outlined.Article),
        Triple(NowPlayingContentState.VISUALIZERS.name, "Visualizers", Icons.Outlined.Explore)
    )
    Column(
        modifier.fillMaxSize(),
        Arrangement.SpaceAround,
        Alignment.CenterHorizontally
    ) {
        IconButton(onPlaylistButtonClick) {
            Icon(Icons.Outlined.FeaturedPlayList, "Playlist")
        }
        Box {
            when (contentState) {
                NowPlayingContentState.SONG -> {
                }
                NowPlayingContentState.LYRICS -> {
                    Lyrics()
                }
                NowPlayingContentState.VISUALIZERS -> {
                    Column {
                        Text(
                            "Amplitudes",
                            Modifier.padding(16.dp, 32.dp),
                            style = MaterialTheme.typography.h6
                        )
                        AmplitudeVisualizer()
                    }
                }
            }
        }
        Column {
            if (contentState == NowPlayingContentState.SONG) {
                Text(
                    song.title.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    song.subtitle.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.caption
                )
            }
        }
        Column {
            Divider(
                Modifier.padding(8.dp),
                MaterialTheme.colors.onSurface.copy(0.08f)
            )
            ChipGroup(
                items,
                { contentState.name == it },
                { onTabClick(NowPlayingContentState.valueOf(it)) },
                Modifier.padding(16.dp)
            )
            PlayController(Modifier.padding(16.dp))
            StatefulProgressIndicator(Modifier.padding(32.dp, 8.dp))
        }
    }
}