package com.example.thecat

import android.app.Application
import android.content.Context
import com.example.thecat.utils.CookieUtils
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CookieUtils.init(this@MyApplication)
        MMKV.initialize(this);
        MyApplication.context = this@MyApplication
    }

    companion object {
        lateinit var context: Context
    }
}