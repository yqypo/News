package com.example.thecat.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thecat.GlideImageLoader
import com.example.thecat.R
import com.example.thecat.databinding.FragmentMineBinding
import com.example.thecat.net.HomeNetworkRepository
import com.example.thecat.net.NetViewModelFactory
import com.example.thecat.ui.activity.CollectionActivity
import com.example.thecat.ui.activity.LoginActivity
import com.example.thecat.utils.UserUtils
import com.example.thecat.vm.LoginViewModel

class MineFragment : Fragment() {
    private lateinit var _binding: FragmentMineBinding
    private val binding: FragmentMineBinding
        get() = _binding
    private lateinit var viewModelFactory:NetViewModelFactory
    private lateinit var viewModel:LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.let { GlideImageLoader(it).loadCircleImage(R.mipmap.ic_launcher, binding.avatar) }
        binding.tvUserName.text = UserUtils.getUserName()
        binding.tvUserName.setOnClickListener {
            if(UserUtils.data.value==false){
                val intent = Intent(this@MineFragment.activity,LoginActivity::class.java)
                startActivity(intent)
            }
        }
        val networkRepository = HomeNetworkRepository()
        viewModelFactory = NetViewModelFactory(networkRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        UserUtils.getLoginStateLiveData().observe(viewLifecycleOwner){
            binding.tvUserName.text = UserUtils.getUserName()
            if(it){
                binding.tvLogout.visibility = View.VISIBLE
                activity?.let { GlideImageLoader(it).loadCircleImage("https://img0.baidu.com/it/u=1096804501,1617897795&fm=253&fmt=auto&app=138&f=JPG?w=500&h=500", binding.avatar) }
            }else{
                binding.tvLogout.visibility = View.GONE
                activity?.let { GlideImageLoader(it).loadCircleImage(R.mipmap.ic_launcher, binding.avatar) }
            }
        }
        binding.llCollect.setOnClickListener {
            if(UserUtils.data.value==false){
                val intent = Intent(this@MineFragment.activity,LoginActivity::class.java)
                startActivity(intent)
                return@setOnClickListener
            }
            val intent = Intent(activity, CollectionActivity::class.java)
            startActivity(intent)
        }
        binding.tvLogout.setOnClickListener {
            viewModel.logOut().observe(viewLifecycleOwner,object :Observer<Any?>{
                override fun onChanged(value: Any?) {
                    UserUtils.logout()
                }
            })
        }
        return view
    }
}