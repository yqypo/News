package com.example.thecat.database.hotkey

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thecat.data.HotKeyEntity

@Database(entities = [ HotKeyEntity:: class], version = 1, exportSchema = false)
abstract class HotkeyDatabase: RoomDatabase(){
    abstract val hotkeyDao : HotKeyDao
    companion object{
        @Volatile
        private var INSTANCE: HotkeyDatabase? = null

        fun getDatabase(context: Context): HotkeyDatabase {
            synchronized(this) {
                // 确保INSTANCE不为null时，不会再次构建数据库实例
                var instance = INSTANCE
                // 如果INSTANCE为null，则构建数据库实例
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HotkeyDatabase:: class.java,
                    "hotkey"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}