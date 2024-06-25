package com.example.thecat.database.hotkey

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.thecat.data.HotKeyEntity

@Dao
interface HotKeyDao {
    // 在插入记录时，如果数据表中已存在该记录，则忽略该记录，并继续其他记录的插入

    @Query("SELECT * FROM hotkey ORDER BY id DESC")
    fun getAll(): LiveData<List<HotKeyEntity>>

    @Transaction
    suspend fun insertAll(hotKeys: List<HotKeyEntity>) {
        // 如果数据大于30条，执行删除前10条再插入
        if (hotKeys.size > 30) {
            deleteOldestHotKeys()
            insertHotKeys(hotKeys)
        } else {
            insertHotKeys(hotKeys)
        }
    }
    @Transaction
    suspend fun insert(hotKey: HotKeyEntity) {
        // 如果数据大于30条，执行删除前10条再插入
        val all = getAll()
        deleteName(hotKey.name)
        if ((all.value?.size?:0) > 30) {
            deleteOldestHotKeys()
            insertHotKey(hotKey)
        } else {
            insertHotKey(hotKey)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHotKeys(hotKeys: List<HotKeyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotKey(hotKeys: HotKeyEntity)

    @Query("DELETE FROM hotkey WHERE id IN (SELECT id FROM hotkey ORDER BY id ASC LIMIT 10)")
    suspend fun deleteOldestHotKeys()
    @Query("DELETE FROM hotkey WHERE name = :name")
    suspend fun deleteName(name: String)
}