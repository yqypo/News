package com.example.thecat.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thecat.database.article.ArticleLocalRepository
import com.example.thecat.vm.HomeViewModel


class ArticleLocalViewModelFactory(
    private val networkRepository: HomeNetworkRepository,
    private val localRepository: ArticleLocalRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return modelClass.getConstructor(
                HomeNetworkRepository::class.java, ArticleLocalRepository::class.java
            )
                .newInstance(networkRepository, localRepository)
        }
        throw IllegalArgumentException("未知的ViewModel类型")
    }
}