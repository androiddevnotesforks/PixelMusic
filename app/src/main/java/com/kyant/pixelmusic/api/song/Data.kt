package com.kyant.pixelmusic.api.song

data class Data(
    val id: Long? = 0,
    val url: String? = "",
    val br: Int? = 0,
    val size: Int? = 0,
    val md5: String? = "",
    val code: Int? = 0,
    val expi: Int? = 0,
    val type: String? = "",
    val gain: Int? = 0,
    val fee: Int? = 0,
    val uf: Any? = Any(),
    val payed: Int? = 0,
    val flag: Int? = 0,
    val canExtend: Boolean? = false,
    val freeTrialInfo: Any? = Any(),
    val level: String? = "",
    val encodeType: String? = "",
    val freeTrialPrivilege: FreeTrialPrivilege? = FreeTrialPrivilege(),
    val urlSource: Int? = 0
)