package com.example.thecat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    val `data`: DataBean,
    val errorCode: Int,
    val errorMsg: String
)
@JsonClass(generateAdapter = true)
data class DataBean(
    val curPage: Int,
    val datas: List<ArticleItem>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
@JsonClass(generateAdapter = true)
@Entity(tableName = "article_table") // 指定表名
data class ArticleItem(
    @PrimaryKey
    val id: Int,
    val author: String,
    val chapterName: String,
    var collect: Boolean,
    val desc: String,
    val envelopePic: String,
    val link: String,
    val niceDate: String,
    val title: String
)
@JsonClass(generateAdapter = true)
data class Tag(
    val name: String,
    val url: String
)