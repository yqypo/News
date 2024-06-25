package com.example.thecat.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.BarUtils
import com.example.thecat.R
import com.example.thecat.navigator.MyFragmentNavigator
import com.example.thecat.utils.UserUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        BarUtils.setNavBarColor(this, Color.TRANSPARENT)
        BarUtils.setNavBarLightMode(this, true)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        //2.自定义FragmentNavigator，mobile_navigation.xml文件中的fragment标识改为自己的fragment
        val fragmentNavigator =
            MyFragmentNavigator(this, navHostFragment.childFragmentManager, navHostFragment.id)
        //3.注册到Navigator里面，这样才找得到
        navController.navigatorProvider.addNavigator(fragmentNavigator)
        //4.设置Graph，需要将activity_main.xml文件中的app:navGraph="@navigation/mobile_navigation"移除
        navController.setGraph(R.navigation.mobile_navigation)
        //5.将NavController和BottomNavigationView绑定，形成联动效果
        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        UserUtils.checkStatus()
    }
}