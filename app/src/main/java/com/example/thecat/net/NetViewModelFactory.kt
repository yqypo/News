package com.example.thecat.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thecat.vm.CollectionViewModel
import com.example.thecat.vm.HomeViewModel
import com.example.thecat.vm.LoginViewModel
import com.example.thecat.vm.SearchViewModel


class NetViewModelFactory(
    private val networkRepository: HomeNetworkRepository,
)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return modelClass.getConstructor(
                HomeNetworkRepository::class.java)
                .newInstance(networkRepository)
        }else if(modelClass.isAssignableFrom(CollectionViewModel::class.java)){
            return modelClass.getConstructor(
                HomeNetworkRepository::class.java)
                .newInstance(networkRepository)
        }else if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return modelClass.getConstructor(
                HomeNetworkRepository::class.java)
                .newInstance(networkRepository)
        }

        throw IllegalArgumentException("未知的ViewModel类型")
    }
}