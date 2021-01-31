package com.kyant.pixelmusic.api.album

data class Artist(
    val img1v1Id: Long? = 0,
    val topicPerson: Int? = 0,
    val followed: Boolean? = false,
    val img1v1Url: String? = "",
    val briefDesc: String? = "",
    val musicSize: Int? = 0,
    val albumSize: Int? = 0,
    val trans: String? = "",
    val alias: List<String>? = listOf(),
    val picId: Long? = 0,
    val picUrl: String? = "",
    val name: String? = "",
    val id: Int? = 0,
    val picId_str: String? = "",
    val img1v1Id_str: String? = ""
)