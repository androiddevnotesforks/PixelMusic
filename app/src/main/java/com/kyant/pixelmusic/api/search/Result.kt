package com.kyant.pixelmusic.api.search

data class Result(
    val songs: List<Song>? = listOf(),
    val hasMore: Boolean? = false,
    val songCount: Int? = 0
)