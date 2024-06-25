package com.example.thecat.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.BarUtils
import com.example.thecat.R
import com.example.thecat.databinding.ActivityLoginBinding
import com.example.thecat.databinding.FragmentLoginBinding
import com.example.thecat.event.EventPageChange
import com.example.thecat.ui.adapter.FixedFragmentPagerAdapter
import com.example.thecat.ui.fragment.LoginFragment
import com.example.thecat.ui.fragment.RegisterFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class LoginActivity: AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private val binding: ActivityLoginBinding
        get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this@LoginActivity)
        _binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        BarUtils.setNavBarColor(this, Color.TRANSPARENT)
        BarUtils.setNavBarLightMode(this, true)
        binding.ivBack.setOnClickListener {
            finish()
        }
        val adapter = FixedFragmentPagerAdapter(supportFragmentManager)
        binding.vp.adapter = adapter
        adapter.setFragmentList(LoginFragment(),RegisterFragment())
        binding.lav.open {
            binding.lav.randomBlink()
        }
    }
    @Subscribe
    fun onEventMainThread(event: EventPageChange){
        binding.vp.currentItem = event.page
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this@LoginActivity)
    }



}