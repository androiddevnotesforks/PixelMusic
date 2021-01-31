package com.kyant.pixelmusic.api.lyrics

data class LyricUser(
    val id: Long? = 0,
    val status: Int? = 0,
    val demand: Int? = 0,
    val userid: Long? = 0,
    val nickname: String? = "",
    val uptime: Long? = 0
)