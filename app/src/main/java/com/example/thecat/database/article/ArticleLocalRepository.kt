package com.example.thecat.database.article

import com.example.thecat.data.ArticleItem

class ArticleLocalRepository(
    private val dao : ArticleDao
){
    val articleList = dao.getAll()
    suspend fun insertAll(list:List<ArticleItem>) = dao.insertAll(list)

}
