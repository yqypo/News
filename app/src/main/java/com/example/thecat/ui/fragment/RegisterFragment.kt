package com.example.thecat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.example.thecat.databinding.FragmentRegisterBinding
import com.example.thecat.event.EventPageChange
import com.example.thecat.net.HomeNetworkRepository
import com.example.thecat.net.NetViewModelFactory
import com.example.thecat.vm.LoginViewModel
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus

class RegisterFragment: Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding: FragmentRegisterBinding
        get() = _binding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: NetViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.tvTitle.setOnClickListener {
            EventBus.getDefault().post(EventPageChange(0))
        }
        val networkRepository = HomeNetworkRepository()
        viewModelFactory = NetViewModelFactory(networkRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.tvRegister.setOnClickListener {
            val userName = binding.etName.text.toString()
            val pwd = binding.etPwd.text.toString()
            val rePwd = binding.etRePwd.text.toString()
            if(userName.isEmpty()){
                ToastUtils.showShort("请输入用户名！")
                return@setOnClickListener
            }
            if(pwd.isEmpty()){
                ToastUtils.showShort("请输入密码！")
                return@setOnClickListener
            }
            if(rePwd.isEmpty()){
                ToastUtils.showShort("请再次输入密码！")
                return@setOnClickListener
            }
            if(pwd!=rePwd){
                ToastUtils.showShort("两次密码输入不一致！")
                return@setOnClickListener
            }
            viewModel.register(userName, pwd,rePwd).observe(viewLifecycleOwner) {
                it?.let {
                    MMKV.defaultMMKV().putString("userName", it?.data?.publicName)
                    MMKV.defaultMMKV().putInt("id", it.data?.id?:0)
                    MMKV.defaultMMKV().putString("collection", it.data?.collectIds?.toString())
                    activity?.finish()
                }
            }
        }
        return view
    }
}