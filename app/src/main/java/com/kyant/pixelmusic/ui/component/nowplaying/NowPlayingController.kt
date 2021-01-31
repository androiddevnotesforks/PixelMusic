package com.kyant.pixelmusic.ui.component.nowplaying

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalePixelPlayer
import com.kyant.pixelmusic.locals.Media

@Composable
fun NowPlayingController(modifier: Modifier = Modifier) {
    val player = LocalePixelPlayer.current
    Row(modifier) {
        IconButton({}) {
            Icon(Icons.Outlined.FavoriteBorder, "Favorite")
        }
        Spacer(Modifier.preferredWidth(16.dp))
        IconButton({ player.previous() }) {
            Icon(Icons.Outlined.SkipPrevious, "Skip to prevoius")
        }
        Spacer(Modifier.preferredWidth(16.dp))
        IconButton(
            {
                if (Media.browser.isConnected) {
                    Media.session?.isActive = true
                    player.playOrPause()
                }
            },
            Modifier.background(
                MaterialTheme.colors.primary,
                RoundedCornerShape(50)
            )
        ) {
            Icon(
                if (player.isPlayingState) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
                if (player.isPlayingState) "Pause" else "Play",
                tint = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(Modifier.preferredWidth(16.dp))
        IconButton({ player.next() }) {
            Icon(Icons.Outlined.SkipNext, "Skip to next")
        }
        Spacer(Modifier.preferredWidth(16.dp))
        IconButton({}) {
            Icon(Icons.Outlined.Info, "Info")
        }
    }
}

@Composable
fun NowPlayingControllerCompact(modifier: Modifier = Modifier) {
    val player = LocalePixelPlayer.current
    Row(modifier) {
        IconButton({ player.previous() }) {
            Icon(Icons.Outlined.SkipPrevious, "Skip to prevoius")
        }
        Spacer(Modifier.preferredWidth(8.dp))
        IconButton(
            {
                if (Media.browser.isConnected) {
                    Media.session?.isActive = true
                    player.playOrPause()
                }
            },
            Modifier.background(
                MaterialTheme.colors.primary,
                RoundedCornerShape(50)
            )
        ) {
            Icon(
                if (player.isPlayingState) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
                if (player.isPlayingState) "Pause" else "Play",
                tint = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(Modifier.preferredWidth(8.dp))
        IconButton({ player.next() }) {
            Icon(Icons.Outlined.SkipNext, "Skip to next")
        }
    }
}