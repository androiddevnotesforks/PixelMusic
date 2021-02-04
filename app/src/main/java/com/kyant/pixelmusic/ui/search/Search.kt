package com.kyant.pixelmusic.ui.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.kyant.pixelmusic.locals.LocalSearchResult
import com.kyant.pixelmusic.locals.ProvideSearchResult
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.component.Song
import com.kyant.inimate.layer.TopSheet

@Composable
fun Search(
    visible: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf(TextFieldValue("Happy New Year")) }
    ProvideSearchResult(value.text) {
        val result = LocalSearchResult.current.result?.songs
        TopSheet(visible, modifier) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton({ visible.value = false }) {
                    Icon(Icons.Outlined.Close, "Close")
                }
                OutlinedTextField(
                    value,
                    { value = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    singleLine = true
                )
            }
            LazyColumn {
                result?.let { songs ->
                    items(songs) {
                        Song(it.toSong())
                    }
                }
            }
        }
    }
}