package com.kyant.pixelmusic.api.playlist

data class Subscriber(
    val defaultAvatar: Boolean? = false,
    val province: Int? = 0,
    val authStatus: Int? = 0,
    val followed: Boolean? = false,
    val avatarUrl: String? = "",
    val accountStatus: Int? = 0,
    val gender: Int? = 0,
    val city: Int? = 0,
    val birthday: Long? = 0,
    val userId: Long? = 0,
    val userType: Int? = 0,
    val nickname: String? = "",
    val signature: String? = "",
    val description: String? = "",
    val detailDescription: String? = "",
    val avatarImgId: Long? = 0,
    val backgroundImgId: Long? = 0,
    val backgroundUrl: String? = "",
    val authority: Int? = 0,
    val mutual: Boolean? = false,
    val expertTags: Any? = Any(),
    val experts: Any? = Any(),
    val djStatus: Int? = 0,
    val vipType: Int? = 0,
    val remarkName: Any? = Any(),
    val authenticationTypes: Int? = 0,
    val avatarDetail: Any? = Any(),
    val anchor: Boolean? = false,
    val avatarImgIdStr: String? = "",
    val backgroundImgIdStr: String? = "",
    val avatarImgId_str: String? = ""
)