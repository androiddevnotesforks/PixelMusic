package com.kyant.pixelmusic.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TwoToneCard(
    color: Color,
    text: String,
    icon: ImageVector?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        RoundedCornerShape(16.dp),
        color.copy(0.05f),
        elevation = 0.dp
    ) {
        Row(
            Modifier
                .clickable {}
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    it,
                    contentDescription,
                    tint = color
                )
                Spacer(Modifier.preferredWidth(32.dp))
            }
            Text(
                text,
                color = color,
                style = MaterialTheme.typography.h5
            )
        }
    }
}