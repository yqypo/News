package com.example.thecat.net

import com.example.thecat.data.Article
import com.example.thecat.data.CollectionArticle
import com.example.thecat.data.LogInInfo
import com.example.thecat.data.SearchHotKey
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppApiService {

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page:Int): Call<Article>

    @GET("lg/collect/list/{page}/json")
    fun collectList(@Path("page") page:Int): Call<CollectionArticle>
    /**
     * 登录
     * 方法： POST
     * 参数：
     * username，password
     * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String?, @Field("password") password: String?): Call<LogInInfo>

    /**
     * 注册
     * 方法： POST
     * 参数：
     * username，password,repassword
     */
    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassword: String?
    ) : Call<LogInInfo>

    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Call<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Call<Any?>


    @GET("user/logout/json")
    fun logout(): Call<Any?>


    @GET("hotkey/json")
    fun getSearchHotKey():Call<SearchHotKey?>

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun search(
        @Path("page") page: Int,
        @Field("k") key: String?
    ): Call<Article?>
}