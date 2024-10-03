package com.whgarcia.notas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Notas (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "create_date")
    val create_date: String,
    @ColumnInfo(name = "edit_date")
    val edit_date: String,
    @ColumnInfo(name = "delete_note")
    val delete: Boolean = false,
    @ColumnInfo(name = "color_note")
    val color_note: Int
)