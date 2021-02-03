package com.kyant.pixelmusic.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*

class DataStore(val context: Context, val name: String) {
    inline val path: String get() = context.noBackupFilesDir.path
    private inline val file get() = File(listOf(path, name).joinToString(File.separator))
    inline val String.coordinated: String get() = replace(File.separator.toRegex(), "|")
    inline val String.path: String
        get() = listOf(this@DataStore.path, name, this).joinToString(File.separator)

    inline fun <reified T> String.stream(): T {
        return ObjectInputStream(FileInputStream(File(this.path))).readObject() as T
    }

    fun <T> write(key: String, content: T) {
        try {
            val file = File(key.coordinated.path).apply {
                delete()
                createNewFile()
            }
            ObjectOutputStream(FileOutputStream(file)).apply {
                writeObject(content)
                close()
            }
        } catch (e: IOException) {
            error(e)
        }
    }

    fun writeBitmap(key: String, bitmap: Bitmap) {
        try {
            FileOutputStream(File(key.coordinated.path)).apply {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                flush()
                close()
            }
        } catch (e: IOException) {
            error(e)
        }
    }

    fun <T> writeWhileNotExist(key: String, content: T) {
        if (!contains(key)) {
            write(key, content)
        }
    }

    fun writeBitmapWhileNotExist(key: String, bitmap: Bitmap) {
        if (!contains(key)) {
            writeBitmap(key, bitmap)
        }
    }

    inline operator fun <reified T> get(key: String, def: T? = null): T? {
        try {
            return key.coordinated.stream() ?: def
        } catch (e: IOException) {
            error(e)
        } catch (e: ClassNotFoundException) {
            error(e)
        }
    }

    fun getBitmap(key: String, def: Bitmap? = null): Bitmap? {
        return BitmapFactory.decodeFile(key.coordinated.path) ?: def
    }

    fun contains(key: String): Boolean = File(requirePath(key)).exists()

    fun requirePath(key: String): String = key.coordinated.path

    fun clear(key: String? = null) {
        if (key == null) {
            file.deleteOnExit()
        } else {
            File(key.coordinated.path).delete()
        }
    }

    init {
        file.mkdirs()
    }
}