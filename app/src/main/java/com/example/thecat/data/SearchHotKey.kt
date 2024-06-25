package com.example.thecat.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SearchHotKey(
    val `data`: List<HotKey>,
    val errorCode: Int,
    val errorMsg: String
)
@JsonClass(generateAdapter = true)
data class HotKey(
    val id: Int,
    val link: String?,
    val name: String?,
    val order: Int?,
    val visible: Int?
)
@Entity(tableName = "hotkey")
class HotKeyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,
)