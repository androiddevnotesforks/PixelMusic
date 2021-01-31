package com.kyant.pixelmusic.api.album

data class CommentThread(
    val id: String? = "",
    val resourceInfo: ResourceInfo? = ResourceInfo(),
    val resourceType: Int? = 0,
    val commentCount: Int? = 0,
    val likedCount: Int? = 0,
    val shareCount: Int? = 0,
    val hotCount: Int? = 0,
    val latestLikedUsers: Any? = Any(),
    val resourceOwnerId: Int? = 0,
    val resourceTitle: String? = "",
    val resourceId: Int? = 0
)