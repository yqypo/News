package com.example.thecat.net

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.example.thecat.MyApplication
import com.example.thecat.data.Article
import com.example.thecat.data.CollectionArticle
import com.example.thecat.data.LogInInfo
import com.example.thecat.data.SearchHotKey
import com.example.thecat.utils.CookieUtils
import com.tencent.smtt.utils.Apn
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class HomeNetworkRepository() {
    private val baseUrl = "https://www.wanandroid.com/"

    private val retrofitMoshi by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(CookieUtils.cookieJar)
            .build()
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AppApiService::class.java)
    }

    suspend fun getArticleList(page:Int = 0): Article? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.getArticleList(page)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<Article> {

                override fun onFailure(call: Call<Article>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<Article>,
                    response: Response<Article>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        continuation.resume(null)
                    }
                }

            })

        }
    }


    suspend fun getCollectList(page:Int = 0): CollectionArticle? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.collectList(page)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<CollectionArticle> {

                override fun onFailure(call: Call<CollectionArticle>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<CollectionArticle>,
                    response: Response<CollectionArticle>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        continuation.resume(null)
                    }
                }

            })

        }
    }

    suspend fun login(userName:String,pwd:String): LogInInfo? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.login(userName,pwd)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<LogInInfo> {

                override fun onFailure(call: Call<LogInInfo>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<LogInInfo>,
                    response: Response<LogInInfo>
                ) {
                    if (response.isSuccessful&&response.body()?.data!=null) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        ToastUtils.showShort(response.body()?.errorMsg)
                        continuation.resume(null)
                    }
                }

            })

        }
    }
    suspend fun register(userName:String,pwd:String,rePwd:String): LogInInfo? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.register(userName,pwd,rePwd)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<LogInInfo> {

                override fun onFailure(call: Call<LogInInfo>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<LogInInfo>,
                    response: Response<LogInInfo>
                ) {
                    if (response.isSuccessful&&response.body()?.data!=null) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        ToastUtils.showShort(response.body()?.errorMsg)
                        continuation.resume(null)
                    }
                }

            })

        }
    }

    suspend fun collectArticle(id:Int): Any? {
        return suspendCancellableCoroutine { continuation ->

            val call = retrofitMoshi.collectArticle(id)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<Any?> {

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        ToastUtils.showShort("收藏失败")
                        continuation.resume(null)
                    }
                }

            })

        }
    }
    suspend fun unCollectArticle(id:Int): Any? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.unCollectArticle(id)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<Any?> {

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        ToastUtils.showShort("收藏失败")
                        continuation.resume(null)
                    }
                }

            })

        }
    }


    suspend fun logout(): Any? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.logout()
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<Any?> {

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }

                }

                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                    if (response.isSuccessful) {
                        ToastUtils.showShort("退出登录成功！")
                        continuation.resume(response.body())
                    } else {
                        continuation.resume(null)
                    }
                }

            })

        }
    }
    suspend fun getSearchHotKey(): SearchHotKey? {
        return suspendCancellableCoroutine { continuation ->
            val call = retrofitMoshi.getSearchHotKey()
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<SearchHotKey?> {

                override fun onFailure(call: Call<SearchHotKey?>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }

                }

                override fun onResponse(
                    call: Call<SearchHotKey?>,
                    response: Response<SearchHotKey?>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        continuation.resume(null)
                    }
                }

            })

        }
    }


    suspend fun search(page:Int = 0,key:String): Article? {
        return suspendCancellableCoroutine { continuation ->

            val call = retrofitMoshi.search(page,key)
            // 异步发送请求，并通知回调方法处理响应
            call.enqueue(object : Callback<Article?> {

                override fun onFailure(call: Call<Article?>, t: Throwable) {
                    Log.e("网络错误", t.toString(), t)
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<Article?>,
                    response: Response<Article?>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        Log.e(
                            "HTTP响应错误",
                            "获取数据失败 \n ${response.errorBody()?.string() ?: ""}"
                        )
                        continuation.resume(null)
                    }
                }

            })

        }
    }

}





