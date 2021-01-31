package com.kyant.pixelmusic.api.album

data class Info(
    val commentThread: CommentThread? = CommentThread(),
    val latestLikedUsers: Any? = Any(),
    val liked: Boolean? = false,
    val comments: Any? = Any(),
    val resourceType: Int? = 0,
    val resourceId: Int? = 0,
    val commentCount: Int? = 0,
    val likedCount: Int? = 0,
    val shareCount: Int? = 0,
    val threadId: String? = ""
)