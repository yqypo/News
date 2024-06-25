package com.example.thecat.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.thecat.R
import com.example.thecat.data.ArticleItem
import com.example.thecat.data.HotKey
import com.example.thecat.data.HotKeyEntity
import com.example.thecat.database.hotkey.HotkeyDatabase
import com.example.thecat.database.hotkey.HotKeyLocalRepository
import com.example.thecat.databinding.ActivitySearchBinding
import com.example.thecat.net.HomeNetworkRepository
import com.example.thecat.net.LocalViewModelFactory
import com.example.thecat.ui.adapter.HomeArticleAdapter
import com.example.thecat.ui.adapter.SearchHistoryAdapter
import com.example.thecat.ui.adapter.SearchHotKeyAdapter
import com.example.thecat.ui.view.CollectView
import com.example.thecat.vm.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchActivity : AppCompatActivity(), SearchHotKeyAdapter.OnItemClickListener,
    HomeArticleAdapter.OnCollectListener, SearchHistoryAdapter.OnItemClickListener {
    private lateinit var _binding: ActivitySearchBinding
    private val binding: ActivitySearchBinding
        get() = _binding
    private val MODE_HOT = 0
    private val MODE_SEARCH = 1
    private var MODE = MODE_HOT
    private lateinit var viewModelFactory: LocalViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private lateinit var rvHotAdapter: SearchHotKeyAdapter
    private lateinit var searchListAdapter: HomeArticleAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        BarUtils.setNavBarColor(this, Color.TRANSPARENT)
        BarUtils.setNavBarLightMode(this, true)
        BarUtils.addMarginTopEqualStatusBarHeight(binding.vSystembar)
        val networkRepository = HomeNetworkRepository()
        val localRepository = HotKeyLocalRepository(
            HotkeyDatabase.getDatabase(application).hotkeyDao)
        viewModelFactory = LocalViewModelFactory(networkRepository,localRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if ((p0?.length ?: 0) == 0) {
                    MODE = MODE_HOT
                    UIChange()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.etContent.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (textView.text.toString().isBlank()) {
                    ToastUtils.showShort("请输入关键字！")
                    return@setOnEditorActionListener true
                }
                MODE = MODE_SEARCH
                viewModel.search(textView.text.toString())
                KeyboardUtils.hideSoftInput(textView)
                true
            } else {
                false
            }
        }
        binding.ivSearch.setOnClickListener {
            if (binding.etContent.text.toString().isBlank()) {
                ToastUtils.showShort("请输入关键字！")
                return@setOnClickListener
            }
            MODE = MODE_SEARCH
            viewModel.search(binding.etContent.text.toString())
            KeyboardUtils.hideSoftInput(binding.etContent)
        }
        initView()
        initData()
    }


    fun initData() {
        viewModel.searchHotKeyLiveData.observe(this@SearchActivity) {
            rvHotAdapter.setData(it)
            UIChange()
        }
        viewModel.searchListLiveData.observe(this@SearchActivity) {
            searchListAdapter.setData(it)
            UIChange()
        }
        viewModel.localHistory.observeForever{
            searchHistoryAdapter.setData(it)
        }
        viewModel.getHotSearchKey()
    }

    fun initView() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (MODE == MODE_HOT) {
                    finish()
                } else {
                    MODE = MODE_HOT
                    UIChange()
                }
            }
        }
        // Add callback to the OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, callback)
        binding.apply {
            rvHot.layoutManager = FlexboxLayoutManager(this@SearchActivity)
            rvHotAdapter = SearchHotKeyAdapter().apply {
                listener = this@SearchActivity
            }
            rvHot.adapter = rvHotAdapter
            rvSearch.layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            searchListAdapter = HomeArticleAdapter().apply {
                listener = this@SearchActivity
            }
            rvSearch.adapter = searchListAdapter
            rvSearchHistory.layoutManager = FlexboxLayoutManager(this@SearchActivity)
            searchHistoryAdapter = SearchHistoryAdapter().apply {
                listener = this@SearchActivity
            }
            rvSearchHistory.adapter= searchHistoryAdapter
        }
    }


    fun UIChange() {
        binding.apply {
            if (MODE == MODE_HOT) {
                tvHotTitle.visibility = if (rvHotAdapter.itemCount > 0) View.VISIBLE else View.GONE
                rvHot.visibility = if (rvHotAdapter.itemCount > 0) View.VISIBLE else View.GONE
                tvSearchTitleHistory.visibility =
                    if (searchHistoryAdapter.itemCount > 0) View.VISIBLE else View.GONE
                rvSearchHistory.visibility =
                    if (searchHistoryAdapter.itemCount>0) View.VISIBLE else View.GONE
                rvSearch.visibility = View.GONE
            } else {
                tvHotTitle.visibility = View.GONE
                rvHot.visibility = View.GONE
                tvSearchTitleHistory.visibility = View.GONE
                rvSearchHistory.visibility = View.GONE
                rvSearch.visibility = View.VISIBLE
            }
        }
    }

    override fun onClickHotKeyListener(item: HotKey?) {
        binding.etContent.setText(item?.name ?: "")
        binding.etContent.setSelection(binding.etContent.length())
        KeyboardUtils.hideSoftInput(binding.etContent)
        MODE = MODE_SEARCH
        UIChange()
        viewModel.search(item?.name ?: "")
    }

    override fun collect(item: ArticleItem?, v: CollectView?) {
        viewModel.collectArticle(item?.id ?: 0).apply {
            observe(this@SearchActivity, object : Observer<Boolean> {
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
            observe(this@SearchActivity, object : Observer<Boolean> {
                override fun onChanged(boolean: Boolean) {
                    item?.collect = boolean
                    v?.isChecked = boolean
                    removeObserver(this)
                }
            })
        }
    }

    override fun onClickHotKeyEntityListener(item: HotKeyEntity?) {
        binding.etContent.setText(item?.name ?: "")
        binding.etContent.setSelection(binding.etContent.length())
        KeyboardUtils.hideSoftInput(binding.etContent)
        MODE = MODE_SEARCH
        UIChange()
        viewModel.search(item?.name ?: "")
    }

}