package com.kyant.pixelmusic.util

import android.content.Context
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

    fun <T> writeWhileNotExist(key: String, content: T) {
        if (!File(requirePath(key)).exists()) {
            write(key, content)
        }
    }

    fun requirePath(key: String): String = key.coordinated.path

    inline operator fun <reified T> get(key: String, def: T? = null): T? {
        try {
            return key.coordinated.stream() ?: def
        } catch (e: IOException) {
            error(e)
        } catch (e: ClassNotFoundException) {
            error(e)
        }
    }

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