package com.example.thecat.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecat.data.CollectionArticleItem
import com.example.thecat.net.HomeNetworkRepository
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val networkRepository: HomeNetworkRepository,
) : ViewModel() {
    val data = MutableLiveData<List<CollectionArticleItem>?>()
    var page = 0
    val collectLiveData = MutableLiveData<Boolean>()

    /**
     * 网络请求获取数据
     */
    fun refresh(){
        page = 0
        loadMore()
    }
   fun loadMore(){
        viewModelScope.launch {
            networkRepository.getCollectList(page).let { list ->
                data.value = list?.data?.datas
                page++
            }
        }
   }
    fun unCollectArticle(id:Int): MutableLiveData<Boolean> {
        viewModelScope.launch {
            networkRepository.unCollectArticle(id).let {
                it?.let {
                    collectLiveData.value = false
                }
            }
        }
        return collectLiveData
    }
}