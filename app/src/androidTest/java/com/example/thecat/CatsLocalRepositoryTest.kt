package com.example.thecat

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.thecat.data.Article
import com.example.thecat.net.HomeNetworkRepository
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CatsPictureRepositoryTest {
    private lateinit var catsNetworkRepository: HomeNetworkRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setup() {

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
    }

    private suspend fun insertDataFromNetWork() {

    }

    @Test
    fun testInsertDataFromNetwork() = runBlocking {
        val latch = CountDownLatch(1)
        var pictures: List<Article>? = null

        insertDataFromNetWork()
        latch.await(6, TimeUnit.SECONDS)
        assertEquals(10, pictures?.size)
        // 再次获取10条网络记录，并插入数据库
        insertDataFromNetWork()
        latch.await(6, TimeUnit.SECONDS)
        var count = 1
        for (p in pictures!!) {
            count++
        }
    }
}