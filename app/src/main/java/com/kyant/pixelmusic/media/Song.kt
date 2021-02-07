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

fun List<SerializedSong>.toSongs(context: Context): List<Song> {
    val urls = map { it.id!! }.findUrls2()
    val songs = mutableListOf<Song>()
    forEachIndexed { index, song ->
        songs += Song(
            song.id,
            song.albumId,
            song.title,
            song.subtitle,
            song.description,
            loadCachedImage(context, song.albumId.toString(), "covers"),
            urls[index]
        )
    }
    return songs
}

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
    loadImageWithDiskCache(album?.id?.findCoverUrl(), album?.id.toString(), "covers"),
    id?.findUrl()
)

@Composable
fun List<Track>.toSongs(): List<Song> {
    val urls = map { it.id!! }.findUrls()
    val songs = mutableListOf<Song>()
    forEachIndexed { index, song ->
        songs += Song(
            song.id,
            song.al?.id,
            song.name,
            song.ar?.map { it.name }?.joinToString(),
            song.al?.name,
            loadImageWithDiskCache(song.al?.id?.findCoverUrl(), song.al?.id.toString(), "covers"),
            urls?.get(index)
        )
    }
    return songs
}