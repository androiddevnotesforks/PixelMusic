package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.component.ChipGroup
import com.kyant.pixelmusic.ui.component.StatefulProgressIndicator
import com.kyant.pixelmusic.ui.player.PlayController

@Composable
fun NowPlayingExpanded(
    modifier: Modifier = Modifier,
    contentState: NowPlayingContentState,
    onTabClick: (NowPlayingContentState) -> Unit
) {
    val items = listOf(
        Triple(NowPlayingContentState.SONG.name, "Song", Icons.Outlined.Audiotrack),
        Triple(NowPlayingContentState.LYRICS.name, "Lyrics", Icons.Outlined.Article),
        Triple(NowPlayingContentState.VISUALIZERS.name, "Visualizers", Icons.Outlined.Explore)
    )

    Column(
        modifier.fillMaxSize(),
        Arrangement.SpaceBetween,
        Alignment.CenterHorizontally
    ) {
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
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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