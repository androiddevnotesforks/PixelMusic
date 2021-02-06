package com.kyant.pixelmusic.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.kyant.pixelmusic.locals.LocalSearchResult
import com.kyant.pixelmusic.locals.ProvideSearchResult
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.song.Song

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Search(modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf(TextFieldValue("Happy New Year")) }
    Column(modifier) {
        ProvideSearchResult(value.text) {
            val result = LocalSearchResult.current.result?.songs
            OutlinedTextField(
                value,
                { value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                singleLine = true
            )
            LazyColumn {
                result?.let { songs ->
                    items(songs, { it.id.toString() }) {
                        Song(it.toSong())
                    }
                }
            }
        }
    }
}