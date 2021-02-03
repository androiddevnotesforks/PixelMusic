package com.kyant.pixelmusic.api.playlist

data class Al(
    val id: Long? = 0,
    val name: String? = "",
    val picUrl: String? = "",
    val tns: List<Any>? = listOf(),
    val pic_str: String? = "",
    val pic: Long? = 0
)