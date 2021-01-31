package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalSearchResult
import com.kyant.pixelmusic.locals.ProvideSearchResult
import com.kyant.pixelmusic.media.toMediaItem
import com.kyant.pixelmusic.ui.component.Song

@Composable
fun Search(modifier: Modifier = Modifier) {
    ProvideSearchResult("Happy New Year") {
        val result = LocalSearchResult.current.result?.songs
        LazyColumn(
            modifier,
            contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
        ) {
            result?.let { songs ->
                items(songs) {
                    Song(it.toMediaItem())
                }
            }
        }
    }
}