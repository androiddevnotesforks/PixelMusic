package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.History
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.component.TwoToneCard
import com.kyant.pixelmusic.ui.theme.androidBlue
import com.kyant.pixelmusic.ui.theme.androidOrange

@Composable
fun My(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        item {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                RoundedCornerShape(16.dp),
                MaterialTheme.colors.primary,
                elevation = 0.dp
            ) {
                Column(
                    Modifier
                        .clickable {}
                        .padding(32.dp)
                ) {
                    Text(
                        "Log in to explore more",
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
        item {
            Text(
                "My",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.body1
            )
        }
        item {
            TwoToneCard(
                androidBlue,
                "History",
                Icons.Outlined.History, "History"
            )
        }
        item {
            TwoToneCard(
                androidOrange,
                "Favorites",
                Icons.Outlined.Favorite, "Favorites"
            )
        }
    }
}