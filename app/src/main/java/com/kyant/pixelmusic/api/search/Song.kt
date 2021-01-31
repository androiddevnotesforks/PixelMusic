package com.kyant.pixelmusic.api.search

data class Song(
    val id: Long? = 0,
    val name: String? = "",
    val artists: List<Artist>? = listOf(),
    val album: Album? = Album(),
    val duration: Int? = 0,
    val copyrightId: Int? = 0,
    val status: Int? = 0,
    val alias: List<String>? = listOf(),
    val rtype: Int? = 0,
    val ftype: Int? = 0,
    val mvid: Int? = 0,
    val fee: Int? = 0,
    val rUrl: Any? = Any(),
    val mark: Long? = 0
)