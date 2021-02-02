package com.kyant.pixelmusic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.R

@Composable
fun TopBar(
    onSearchButtonClick: () -> Unit,
    onMyButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .preferredHeight(48.dp)
            .background(MaterialTheme.colors.surface.copy(0.92f))
            .padding(8.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        IconButton({}) {
            Icon(
                Icons.Outlined.Menu, "Menu",
                tint = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
            )
        }
        Row {
            Image(
                vectorResource(R.drawable.ic_launcher_foreground), null,
                Modifier.preferredHeight(56.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text(
                stringResource(R.string.app_name),
                color = LocalContentColor.current.copy(ContentAlpha.high),
                style = MaterialTheme.typography.h6
            )
        }
        Row {
            IconButton(onSearchButtonClick) {
                Icon(
                    Icons.Outlined.Search, "Search",
                    tint = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            }
            IconButton(onMyButtonClick) {
                Icon(
                    Icons.Outlined.AccountCircle, "My",
                    tint = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            }
        }
    }
}