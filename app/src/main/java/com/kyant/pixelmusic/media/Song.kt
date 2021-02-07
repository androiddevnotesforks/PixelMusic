package com.kyant.pixelmusic.media

import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.net.toUri
import com.kyant.pixelmusic.api.*
import com.kyant.pixelmusic.api.findUrl
import com.kyant.pixelmusic.api.playlist.Track
import com.kyant.pixelmusic.util.loadCachedImage
import com.kyant.pixelmusic.util.loadImageWithDiskCache
import java.io.Serializable

data class Song(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val icon: ImageBitmap? = null,
    val mediaUrl: String? = null
)

data class SerializedSong(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null
) : Serializable

fun Song.serialize(): SerializedSong = SerializedSong(
    id,
    albumId,
    title,
    subtitle,
    description
)

fun SerializedSong.toSong(context: Context): Song = Song(
    id,
    albumId,
    title,
    subtitle,
    description,
    loadCachedImage(context, albumId.toString(), "covers"),
    id?.findUrl2()
)

fun Song.toMediaDescription(): MediaDescriptionCompat = MediaDescriptionCompat.Builder()
    .setMediaId(id.toString())
    .setTitle(title)
    .setSubtitle(subtitle)
    .setDescription(description)
    .setIconBitmap(icon?.asAndroidBitmap())
    .setExtras(Bundle().apply {
        albumId?.let { putLong("albumId", it) }
    })
    .setMediaUri(mediaUrl?.toUri())
    .build()

fun MediaDescriptionCompat.toSong(): Song = Song(
    mediaId?.toLong(),
    extras?.getLong("albumId"),
    title?.toString(),
    subtitle?.toString(),
    description?.toString(),
    iconBitmap?.asImageBitmap(),
    mediaUri.toString()
)

@Composable
fun com.kyant.pixelmusic.api.search.Song.toSong(): Song = Song(
    id,
    album?.id,
    name,
    artists?.map { it.name }?.joinToString(),
    album?.name,
    icon = album?.id
        ?.findCoverUrl()
        ?.loadImageWithDiskCache(album.id.toString(), "covers"),
    mediaUrl = id?.findUrl()
)

@Composable
fun Track.toSong(): Song = Song(
    id,
    al?.id,
    name,
    ar?.map { it.name }?.joinToString(),
    al?.name,
    icon = al?.id
        ?.findCoverUrl()
        ?.loadImageWithDiskCache(al.id.toString(), "covers"),
    mediaUrl = id?.findUrl()
)