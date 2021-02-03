package com.kyant.pixelmusic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadImage

@Composable
fun TopListItem(
    topList: TopList,
    modifier: Modifier = Modifier
) {
    Card(
        Modifier
            .preferredWidth(192.dp)
            .padding(4.dp),
        RoundedCornerShape(8.dp),
        elevation = 3.dp
    ) {
        Column(modifier.padding(16.dp)) {
            Text(
                topList.name.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.preferredHeight(8.dp))
            Text(
                topList.updateFrequency.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun TopListItemCover(
    topList: TopList,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(8.dp)) {
        Image(
            topList.coverImgUrl?.loadImage() ?: EmptyImage,
            topList.name,
            Modifier
                .preferredSize(128.dp)
                .clip(SmoothRoundedCornerShape())
        )
    }
}