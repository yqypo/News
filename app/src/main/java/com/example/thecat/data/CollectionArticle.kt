package com.example.thecat.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CollectionArticle(
    val `data`: CollectionDataBean?,
    val errorCode: Int,
    val errorMsg: String
)
@JsonClass(generateAdapter = true)
data class CollectionDataBean(
    val curPage: Int,
    val datas: List<CollectionArticleItem>?,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
@JsonClass(generateAdapter = true)
data class CollectionArticleItem(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
)