package com.kyant.pixelmusic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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