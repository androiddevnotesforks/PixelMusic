package com.kyant.pixelmusic.api.toplist

data class ArtistTopList(
    val coverUrl: String? = "",
    val name: String? = "",
    val position: Int? = 0,
    val updateFrequency: String? = ""
)