package com.example.thecat.database.hotkey

import com.example.thecat.data.HotKeyEntity

class HotKeyLocalRepository(
    private val dao : HotKeyDao
) {
    val localHotkeyList = dao.getAll()
    suspend fun insertAll(pictures:List<HotKeyEntity>) = dao.insertAll(pictures)
    suspend fun insert(hotkey:HotKeyEntity) = dao.insert(hotkey)

}
