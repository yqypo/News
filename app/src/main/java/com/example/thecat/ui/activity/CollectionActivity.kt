package com.example.thecat.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.example.thecat.R
import com.example.thecat.data.CollectionArticleItem
import com.example.thecat.databinding.ActivityCollectionBinding
import com.example.thecat.net.HomeNetworkRepository
import com.example.thecat.net.NetViewModelFactory
import com.example.thecat.ui.adapter.CollectionArticleAdapter
import com.example.thecat.ui.view.CollectView
import com.example.thecat.vm.CollectionViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class CollectionActivity : AppCompatActivity(), CollectionArticleAdapter.OnCollectListener {
    private lateinit var _binding: ActivityCollectionBinding
    private val binding: ActivityCollectionBinding
        get() = _binding

    private lateinit var viewModel: CollectionViewModel
    private lateinit var viewModelFactory: NetViewModelFactory
    private lateinit var adapter: CollectionArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView<ActivityCollectionBinding>(
                this,
                R.layout.activity_collection
            )
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        BarUtils.setNavBarColor(this, Color.TRANSPARENT)
        BarUtils.setNavBarLightMode(this, true)
        BarUtils.addMarginTopEqualStatusBarHeight(binding.vSystembar)
        binding.ivBack.setOnClickListener {
            finish()
        }
        val linearLayoutManager = LinearLayoutManager(
            this@CollectionActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rv.layoutManager = linearLayoutManager
        adapter = CollectionArticleAdapter().apply {
            listener = this@CollectionActivity
        }
        binding.rv.adapter = adapter


        binding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(p0: RefreshLayout) {
                viewModel.refresh()
            }

            override fun onLoadMore(p0: RefreshLayout) {
                viewModel.loadMore()
            }

        })
        val networkRepository = HomeNetworkRepository()
        viewModelFactory = NetViewModelFactory(networkRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CollectionViewModel::class.java]
        viewModel.data.observe(this, Observer { data ->
            if (viewModel.page == 0) {
                binding.refresh.finishRefresh()
                adapter.setData(data)
            } else {
                binding.refresh.finishLoadMore()
                adapter.addData(data)
            }
        })
        viewModel.refresh()
    }

    override fun uncollect(item: CollectionArticleItem?, adapterPosition: Int, v: CollectView?) {
        viewModel.unCollectArticle(item?.originId ?: 0).observe(
            this@CollectionActivity, object :Observer<Boolean>{
                override fun onChanged(value: Boolean) {
                    adapter.removeData(item, adapterPosition)
                    viewModel.collectLiveData.removeObserver(this)
                }
            }
        )
    }


}