package com.kyant.pixelmusic.api.playlist

data class PlaylistResult(
    val code: Int? = 0,
    val relatedVideos: Any? = Any(),
    val playlist: Playlist? = Playlist(),
    val urls: Any? = Any(),
    val privileges: List<Privilege>? = listOf()
)