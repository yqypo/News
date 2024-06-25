package com.example.thecat.utils

import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tencent.mmkv.MMKV

object UserUtils {
    val data = MutableLiveData<Boolean>(false)

    fun getLoginStateLiveData(): MutableLiveData<Boolean> {
        return data
    }

    fun checkStatus() {
        val userName = MMKV.defaultMMKV().getString("userName", "")
        val id = MMKV.defaultMMKV().getInt("id", -1)
        data.value = userName?.isNotBlank() == true && id != -1
    }

    fun getUserName(): String? {
        return MMKV.defaultMMKV().getString("userName", "去登录") ?: "去登录"
    }

    fun getCollection(): IntArray? {
        val collectionStr = MMKV.defaultMMKV().getString("collection", "[]") ?: "[]"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // 创建一个 JsonAdapter 来解析 int 数组
        val intArrayAdapter: JsonAdapter<IntArray> = moshi.adapter(IntArray::class.java)

        // 解析 JSON 字符串为 int 数组
        val intArray = intArrayAdapter.fromJson(collectionStr)
        return intArray
    }

    fun logout(){
        MMKV.defaultMMKV().clearAll()
        checkStatus()
    }


}