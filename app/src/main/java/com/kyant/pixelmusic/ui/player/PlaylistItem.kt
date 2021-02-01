package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.ui.component.Cover
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape

@Composable
fun PlaylistItem(
    index: Int,
    song: Song,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    Row(
        modifier
            .fillMaxWidth()
            .preferredHeight(64.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                player.seekTo(index, 0)
                player.play()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Cover(
            song,
            Modifier
                .preferredSize(48.dp)
                .clip(SmoothRoundedCornerShape())
        )
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                song.title.toString(),
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.body1
            )
            Spacer(Modifier.preferredHeight(4.dp))
            Text(
                song.subtitle.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun PlaylistNowPlayingItem(
    song: Song,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        RoundedCornerShape(16.dp),
        MaterialTheme.colors.primary,
        elevation = 0.dp
    ) {
        Box(
            Modifier
                .clickable {}
                .padding(16.dp)
        ) {
            Row(
                Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Cover(
                    song,
                    Modifier
                        .preferredSize(56.dp)
                        .clip(SmoothRoundedCornerShape())
                )
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        song.title.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.preferredHeight(4.dp))
                    Text(
                        song.subtitle.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            PlayPauseButton(Modifier.align(Alignment.CenterEnd))
        }
    }
}