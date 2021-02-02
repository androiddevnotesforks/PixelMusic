package com.kyant.pixelmusic.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalSearchResult
import com.kyant.pixelmusic.locals.ProvideSearchResult
import com.kyant.pixelmusic.media.toMediaItem
import com.kyant.pixelmusic.ui.component.Song

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Search(
    visible: Boolean,
    onCloseButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var value by remember { mutableStateOf(TextFieldValue("Happy New Year")) }
    ProvideSearchResult(value.text) {
        val result = LocalSearchResult.current.result?.songs
        AnimatedVisibility(
            visible,
            modifier,
            enter = slideInVertically({ with(density) { -it - 24.dp.roundToPx() } }),
            exit = slideOutVertically({ with(density) { -it - 24.dp.roundToPx() } })
        ) {
            Card(
                Modifier.fillMaxSize(),
                RoundedCornerShape(0.dp),
                elevation = 24.dp
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onCloseButtonClick) {
                            Icon(Icons.Outlined.Close, "Close")
                        }
                        OutlinedTextField(
                            value,
                            { value = it },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            singleLine = true,
                            onImeActionPerformed = { action, controller ->
                                if (action == ImeAction.Search) {
                                    controller?.hideSoftwareKeyboard()
                                }
                            }
                        )
                    }
                    LazyColumn {
                        result?.let { songs ->
                            items(songs) {
                                Song(it.toMediaItem())
                            }
                        }
                    }
                }
            }
        }
    }
}