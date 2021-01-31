package com.kyant.pixelmusic.api.lyrics

data class LyricResult(
    val sgc: Boolean? = false,
    val sfy: Boolean? = false,
    val qfy: Boolean? = false,
    val lyricUser: LyricUser? = LyricUser(),
    val lrc: Lrc? = Lrc(),
    val klyric: Lrc? = Lrc(),
    val tlyric: Lrc? = Lrc(),
    val code: Int? = 0
)