package com.example.thecat.database.article

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.thecat.data.ArticleItem

@Dao
interface ArticleDao {
    // 在插入记录时，如果数据表中已存在该记录，则忽略该记录，并继续其他记录的插入

    @Query("SELECT * FROM article_table ORDER BY id DESC")
    fun getAll(): LiveData<List<ArticleItem>>

    @Transaction
    suspend fun insertAll(hotKeys: List<ArticleItem>) {
        deleteAll()
        insertItem(hotKeys)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(hotKeys: List<ArticleItem>)


    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

}