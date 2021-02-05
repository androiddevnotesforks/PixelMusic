package com.kyant.pixelmusic.ui.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomNav(
    items: List<Triple<String, String, ImageVector>>,
    selected: @Composable (String) -> Boolean,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier,
        MaterialTheme.colors.surface,
        elevation = 0.dp
    ) {
        items.toList().forEach { (route, label, icon) ->
            BottomNavigationItem(
                selected(route),
                { onClick(route) },
                { Icon(icon, label) },
                label = { Text(label) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
            )
        }
    }
}