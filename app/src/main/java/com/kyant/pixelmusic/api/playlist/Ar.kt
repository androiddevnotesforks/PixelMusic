package com.kyant.pixelmusic.api.playlist

data class Ar(
    val id: Int? = 0,
    val name: String? = "",
    val tns: List<Any>? = listOf(),
    val alias: List<Any>? = listOf()
)