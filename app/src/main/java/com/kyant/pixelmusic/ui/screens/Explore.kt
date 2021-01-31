package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.kyant.pixelmusic.api.findTopList
import com.kyant.pixelmusic.ui.component.ChipGroup
import com.kyant.pixelmusic.ui.component.TopListItem
import com.kyant.pixelmusic.util.currentRoute

enum class ExploreScreens { ALL, SONG_TOP_LIST, ARTIST_TOP_LIST }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Explore(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val items = listOf(
        Triple(ExploreScreens.ALL.name, "All", Icons.Outlined.Stars),
        Triple(ExploreScreens.SONG_TOP_LIST.name, "Song Top List", Icons.Outlined.Stars),
        Triple(
            ExploreScreens.ARTIST_TOP_LIST.name,
            "Artist Top List",
            Icons.Outlined.SupervisorAccount
        ),
    )
    Column(modifier.padding(top = 64.dp)) {
        ChipGroup(
            items,
            { navController.currentRoute() == it },
            { navController.navigate(it) },
            Modifier.padding(16.dp)
        )
        NavHost(navController, ExploreScreens.ALL.name) {
            composable(ExploreScreens.ALL.name) {
            }
            composable(ExploreScreens.SONG_TOP_LIST.name) {
                val topList = findTopList()
                LazyVerticalGrid(
                    GridCells.Adaptive(128.dp),
                    contentPadding = PaddingValues(bottom = 64.dp)
                ) {
                    topList?.list?.let { list ->
                        items(list) {
                            TopListItem(it)
                        }
                    }
                }
            }
            composable(ExploreScreens.ARTIST_TOP_LIST.name) {
            }
        }
    }
}