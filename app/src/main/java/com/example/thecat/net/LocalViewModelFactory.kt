package com.example.thecat.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thecat.database.hotkey.HotKeyLocalRepository
import com.example.thecat.vm.SearchViewModel


class LocalViewModelFactory(
    private val networkRepository: HomeNetworkRepository,
    private val localRepository: HotKeyLocalRepository,
)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return modelClass.getConstructor(
                HomeNetworkRepository::class.java, HotKeyLocalRepository::class.java)
                .newInstance(networkRepository,localRepository)
        }
        throw IllegalArgumentException("未知的ViewModel类型")
    }
}