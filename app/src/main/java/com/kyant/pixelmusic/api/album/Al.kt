package com.kyant.pixelmusic.api.album

data class Al(
    val id: Int? = 0,
    val name: String? = "",
    val picUrl: String? = "",
    val pic_str: String? = "",
    val pic: Long? = 0,
    val alia: List<String>? = listOf()
)