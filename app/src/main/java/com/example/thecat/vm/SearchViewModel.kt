package com.example.thecat.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecat.data.ArticleItem
import com.example.thecat.data.HotKey
import com.example.thecat.data.HotKeyEntity
import com.example.thecat.database.hotkey.HotKeyLocalRepository
import com.example.thecat.net.HomeNetworkRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val networkRepository: HomeNetworkRepository,
    private val localRepository: HotKeyLocalRepository,
) : ViewModel() {
    val searchHotKeyLiveData = MutableLiveData<List<HotKey>?>()
    val searchListLiveData = MutableLiveData<List<ArticleItem>?>()
    val collect = MutableLiveData<Boolean>()
    val localHistory = localRepository.localHotkeyList

    val page = 0

    fun getHotSearchKey(): MutableLiveData<List<HotKey>?> {
        viewModelScope.launch {
            networkRepository.getSearchHotKey().let {
                searchHotKeyLiveData.value = it?.data
            }
        }
        return searchHotKeyLiveData
    }

    fun search(key: String): MutableLiveData<List<ArticleItem>?> {
        viewModelScope.launch {
            networkRepository.search(page, key)?.let {
                searchListLiveData.value = it.data.datas
                localRepository.insert(HotKeyEntity(null,key))
            }
        }
        return searchListLiveData
    }

    fun collectArticle(id: Int): MutableLiveData<Boolean> {
        viewModelScope.launch {
            networkRepository.collectArticle(id).let {
                it?.let {
                    collect.value = true
                }
            }
        }
        return collect
    }

    fun unCollectArticle(id: Int): MutableLiveData<Boolean> {
        viewModelScope.launch {
            networkRepository.unCollectArticle(id).let {
                it?.let {
                    collect.value = false
                }
            }
        }
        return collect
    }
}