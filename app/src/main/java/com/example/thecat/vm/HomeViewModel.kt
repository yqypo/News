package com.example.thecat.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.thecat.data.ArticleItem
import com.example.thecat.database.article.ArticleLocalRepository
import com.example.thecat.database.hotkey.HotKeyLocalRepository
import com.example.thecat.net.HomeNetworkRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val networkRepository: HomeNetworkRepository,
    private val localRepository: ArticleLocalRepository,
) : ViewModel() {
    val data = MutableLiveData<List<ArticleItem>?>()
    var page = 0
    val collect = MutableLiveData<Boolean>()
    val localArticleList = localRepository.articleList

    /**
     * 网络请求获取数据
     */
    fun refresh(){
        page = 0
        loadMore()
    }
   fun loadMore(){
        viewModelScope.launch{
            try {
                networkRepository.getArticleList(page).let { list ->
                    data.value = list?.data?.datas
                    list?.data?.datas?.let {
                        localRepository.insertAll(it)
                    }
                    page++
                }
            }catch (e:Exception){
                ToastUtils.showShort("网络错误，请重试！")
            }
        }
   }

    fun collectArticle(id:Int): MutableLiveData<Boolean> {
        viewModelScope.launch {
            networkRepository.collectArticle(id).let {
                it?.let {
                    collect.value = true
                }
            }
        }
        return collect
    }
    fun unCollectArticle(id:Int): MutableLiveData<Boolean> {
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