package com.kyant.pixelmusic.api.search

data class Artist(
    val id: Int? = 0,
    val name: String? = "",
    val picUrl: String? = "",
    val alias: List<Any>? = listOf(),
    val albumSize: Int? = 0,
    val picId: Long? = 0,
    val img1v1Url: String? = "",
    val img1v1: Int? = 0,
    val trans: Any? = Any()
)