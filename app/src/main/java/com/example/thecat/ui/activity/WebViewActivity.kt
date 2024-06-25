package com.example.thecat.ui.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.BarUtils
import com.example.thecat.R
import com.example.thecat.databinding.ActivityWebviewBinding


class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityWebviewBinding>(this, R.layout.activity_webview)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        BarUtils.setNavBarColor(this, Color.TRANSPARENT)
        BarUtils.setNavBarLightMode(this, true)
        BarUtils.addMarginTopEqualStatusBarHeight(binding.topview)
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvTitle.text = intent.getStringExtra("title")
        binding.webview.apply {
            // 启用垂直滚动条覆盖在内容上方
            setVerticalScrollbarOverlay(true)
            // 加载网页时缩放至页面的整体显示
            settings.loadWithOverviewMode = true
            // 支持使用宽视窗（Viewport）
            settings.useWideViewPort = true
            // 启用内置缩放控件
            settings.builtInZoomControls = true
            // 如果Android版本大于11，隐藏缩放控件
            if (Build.VERSION.SDK_INT > 11) {
                settings.displayZoomControls = false
            }
            // 支持缩放
            settings.setSupportZoom(true)
            // 启用DOM存储
            settings.domStorageEnabled = true
            // 设置默认文本编码格式
            settings.defaultTextEncodingName = "utf-8"
            // 启用JavaScript执行
            settings.javaScriptEnabled = true
            // 设置WebViewClient处理URL加载请求
            val myWebViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url ?: "")
                    return true
                }
            }
            webViewClient = myWebViewClient
            // 加载指定URL
            loadUrl(intent.getStringExtra("url") ?: "")

        }
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!binding.webview.canGoBack()) {
                    finish()
                } else {
                    binding.webview.goBack()
                }
            }
        }
        // Add callback to the OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}