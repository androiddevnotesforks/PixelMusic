package com.kyant.pixelmusic.api.album

data class AlbumResult(
    val songs: List<Song>? = listOf(),
    val code: Int? = 0,
    val album: Album? = Album()
)