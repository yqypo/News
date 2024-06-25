package com.example.thecat.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.example.thecat.GlideImageLoader
import com.example.thecat.data.ArticleItem
import com.example.thecat.database.article.ArticleDatabase
import com.example.thecat.database.article.ArticleLocalRepository
import com.example.thecat.glide.ImageLoader
import com.example.thecat.net.HomeNetworkRepository
import com.example.thecat.databinding.FragmentHomeBinding
import com.example.thecat.net.ArticleLocalViewModelFactory
import com.example.thecat.vm.HomeViewModel
import com.example.thecat.ui.activity.SearchActivity
import com.example.thecat.ui.adapter.HomeArticleAdapter
import com.example.thecat.ui.view.CollectView
import com.example.thecat.utils.UserUtils
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener


class HomeFragment : Fragment(), HomeArticleAdapter.OnCollectListener {

    private lateinit var _binding: FragmentHomeBinding
    private val binding: FragmentHomeBinding
        get() = _binding

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: ArticleLocalViewModelFactory
    private lateinit var adapter: HomeArticleAdapter

    private val imageLoader: ImageLoader by lazy {
        GlideImageLoader(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val linearLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rv.layoutManager = linearLayoutManager
        adapter = HomeArticleAdapter().apply {
            listener = this@HomeFragment
        }
        binding.rv.adapter = adapter
        BarUtils.addMarginTopEqualStatusBarHeight(binding.topview)


        binding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(p0: RefreshLayout) {
                viewModel.refresh()
            }

            override fun onLoadMore(p0: RefreshLayout) {
                viewModel.loadMore()
            }

        })
        val networkRepository = HomeNetworkRepository()
        val localRepository = ArticleLocalRepository(
            ArticleDatabase.getDatabase(requireContext().applicationContext).articleDao
        )
        viewModelFactory = ArticleLocalViewModelFactory(networkRepository, localRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.localArticleList.observeForever { data ->
            if (viewModel.page == 0) {
                binding.refresh.finishRefresh()
                adapter.setData(data)
            } else {
                binding.refresh.finishLoadMore()
                adapter.addData(data)
            }
        }
        viewModel.refresh()
        UserUtils.getLoginStateLiveData().observe(viewLifecycleOwner) {
            viewModel.refresh()
        }
        binding.ivSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun collect(item: ArticleItem?, v: CollectView?) {
        viewModel.collectArticle(item?.id ?: 0).apply {
            observe(viewLifecycleOwner, object : Observer<Boolean> {
                override fun onChanged(boolean: Boolean) {
                    item?.collect = boolean
                    v?.isChecked = boolean
                    removeObserver(this)
                }
            })
        }
    }

    override fun uncollect(item: ArticleItem?, v: CollectView?) {
        viewModel.unCollectArticle(item?.id ?: 0).apply {
            observe(viewLifecycleOwner, object : Observer<Boolean> {
                override fun onChanged(boolean: Boolean) {
                    item?.collect = boolean
                    v?.isChecked = boolean
                    removeObserver(this)
                }
            })
        }
    }
}

