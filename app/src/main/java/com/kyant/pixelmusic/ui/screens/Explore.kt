package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findTopList
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.ui.playlist.TopSongItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Explore(
    state: SwipeableState<Boolean>,
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val topLists = findTopList()
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {},
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    "Top Songs",
                    Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1
                )
                IconButton({}) {
                    Icon(Icons.Outlined.KeyboardArrowRight, "See more")
                }
            }
        }
        item {
            LazyRow(contentPadding = PaddingValues(64.dp, 16.dp)) {
                topLists?.list?.let { list ->
                    items(list) {
                        TopSongItem(it, {
                            CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                                state.animateTo(true)
                            }
                            topList.value = it
                        })
                    }
                }
            }
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {},
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    "Top Artists",
                    Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1
                )
                IconButton({}) {
                    Icon(Icons.Outlined.KeyboardArrowRight, "See more")
                }
            }
        }
    }
}