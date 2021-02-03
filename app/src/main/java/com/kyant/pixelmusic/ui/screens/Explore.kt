package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findTopList
import com.kyant.pixelmusic.ui.component.TopListItem

@Composable
fun Explore(modifier: Modifier = Modifier) {
    val topList = findTopList()
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
                    "Top Lists",
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
                topList?.list?.let { list ->
                    items(list) {
                        TopListItem(it)
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