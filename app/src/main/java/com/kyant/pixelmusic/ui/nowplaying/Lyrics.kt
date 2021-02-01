package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalLyrics
import com.kyant.pixelmusic.util.isCurrentLine

@Composable
fun Lyrics(modifier: Modifier = Modifier) {
    val sortedLyrics = LocalLyrics.current.toList().sortedBy { it.first }
    LazyColumn(
        modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(sortedLyrics) { (time, lyric) ->
            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = if (time.isCurrentLine(sortedLyrics.toMap())) MaterialTheme.colors.secondary
                            else Color.Transparent
                        )
                    ) {
                        append("▶")
                    }
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("$time ")
                    }
                    append(lyric)
                },
                style = MaterialTheme.typography.body2
            )
            Spacer(Modifier.preferredHeight(6.dp))
        }
    }
}