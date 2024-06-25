package com.example.thecat.data

import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
class LogInInfo(
    val data: Data?,
    val errorCode: Int,
    val errorMsg: String
)

@JsonClass(generateAdapter = true)
data class Data(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)