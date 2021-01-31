package com.kyant.pixelmusic.api.toplist

data class TopListResult(
    val code: Int? = 0,
    val list: List<TopList>? = listOf(),
    val artistToplist: ArtistTopList? = ArtistTopList()
)