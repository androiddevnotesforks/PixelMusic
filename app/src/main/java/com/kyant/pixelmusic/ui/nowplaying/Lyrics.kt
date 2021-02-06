package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.EmptyLyrics
import com.kyant.pixelmusic.api.findLyrics
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.util.isCurrentLine

@Composable
fun Lyrics(modifier: Modifier = Modifier) {
    val song = LocalNowPlaying.current
    val sortedLyrics = (song.id?.findLyrics() ?: EmptyLyrics).toList().sortedBy { it.first }
    val state = rememberLazyListState().apply {

    }
    LazyColumn(
        modifier.fillMaxWidth(),
        state,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(sortedLyrics) { (time, lyric) ->
            Text(
                lyric,
                Modifier.padding(16.dp),
                if (time.isCurrentLine(sortedLyrics.toMap())) MaterialTheme.colors.secondary
                else MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled),
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.preferredHeight(6.dp))
        }
    }
}