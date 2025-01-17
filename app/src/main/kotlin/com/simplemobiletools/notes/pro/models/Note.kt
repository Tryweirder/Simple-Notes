package com.simplemobiletools.notes.pro.models

import android.content.Context
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "notes", indices = [(Index(value = ["id"], unique = true))])
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "value") var value: String,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "path") var path: String = "") {

    fun getNoteStoredValue(context: Context): String? {
        return if (path.isNotEmpty()) {
            try {
                if (path.startsWith("content://")) {
                    val inputStream = context.contentResolver.openInputStream(Uri.parse(path))
                    inputStream?.bufferedReader().use { it!!.readText() }
                } else {
                    File(path).readText()
                }
            } catch (e: Exception) {
                null
            }
        } else {
            value
        }
    }
}
