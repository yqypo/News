package com.example.thecat.database.article

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thecat.data.ArticleItem
import com.example.thecat.data.HotKeyEntity

@Database(entities = [ ArticleItem:: class], version = 1, exportSchema = false)
abstract class ArticleDatabase: RoomDatabase(){
    abstract val articleDao : ArticleDao
    companion object{
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            synchronized(this) {
                // 确保INSTANCE不为null时，不会再次构建数据库实例
                var instance = INSTANCE
                // 如果INSTANCE为null，则构建数据库实例
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase:: class.java,
                    "article_table"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}