package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Equalizer
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.theme.androidOrange
import com.kyant.pixelmusic.ui.theme.googleBlue
import com.kyant.pixelmusic.ui.theme.googleRed

@Composable
fun Home(modifier: Modifier = Modifier) {
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
                MaterialTheme.colors.secondary,
                elevation = 0.dp
            ) {
                Column(
                    Modifier
                        .clickable {}
                        .padding(32.dp)
                ) {
                    Text(
                        "Pixel Music Pre-Alpha",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(Modifier.preferredHeight(8.dp))
                    Text(
                        "Still developing...",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
        item {
            Text(
                "Try new functions",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.body1
            )
        }
        item {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                RoundedCornerShape(16.dp),
                googleBlue.copy(0.04f),
                elevation = 0.dp
            ) {
                Row(
                    Modifier
                        .clickable {}
                        .padding(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        rememberVectorPainter(Icons.Outlined.Search),
                        "Search",
                        tint = googleBlue
                    )
                    Spacer(Modifier.preferredWidth(32.dp))
                    Text(
                        "Universal Search",
                        color = googleBlue,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
        item {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                RoundedCornerShape(16.dp),
                androidOrange.copy(0.04f),
                elevation = 0.dp
            ) {
                Row(
                    Modifier
                        .clickable {}
                        .padding(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        rememberVectorPainter(Icons.Outlined.Equalizer),
                        "Tops",
                        tint = androidOrange
                    )
                    Spacer(Modifier.preferredWidth(32.dp))
                    Text(
                        "Top Lists",
                        color = androidOrange,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
        item {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                RoundedCornerShape(16.dp),
                googleRed.copy(0.04f),
                elevation = 0.dp
            ) {
                Row(
                    Modifier
                        .clickable {}
                        .padding(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        rememberVectorPainter(Icons.Outlined.BugReport),
                        "Bug",
                        tint = googleRed
                    )
                    Spacer(Modifier.preferredWidth(32.dp))
                    Text(
                        "Crash your system",
                        color = googleRed,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
    }
}