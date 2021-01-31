package com.kyant.pixelmusic.api.search

data class Album(
    val id: Long? = 0,
    val name: String? = "",
    val artist: Artist? = Artist(),
    val publishTime: Long? = 0,
    val size: Int? = 0,
    val copyrightId: Int? = 0,
    val status: Int? = 0,
    val picId: Long? = 0,
    val mark: Int? = 0,
    val alia: List<String>? = listOf()
)