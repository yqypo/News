package com.example.thecat.ui.adapter

import android.content.Intent
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecat.GlideImageLoader
import com.example.thecat.data.ArticleItem
import com.example.thecat.databinding.ItemArticleBinding
import com.example.thecat.glide.ImageLoader
import com.example.thecat.ui.activity.LoginActivity
import com.example.thecat.ui.activity.WebViewActivity
import com.example.thecat.ui.view.CollectView
import com.example.thecat.utils.UserUtils

/**
 * recyclerview 适配器
 */
class HomeArticleAdapter : RecyclerView.Adapter<HomeArticleAdapter.ViewHolder>() {
    var list = mutableListOf<ArticleItem>()
    var listener: OnCollectListener? = null
    fun setData(datas: List<ArticleItem>?) {
        list.clear()
        datas?.let { list.addAll(it) }
        notifyDataSetChanged()
    }

    fun addData(datas: List<ArticleItem>?) {
        datas?.let { list.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position), listener)

    }

    class ViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val imageLoader: ImageLoader by lazy {
            GlideImageLoader(binding.root.context)
        }

        fun bind(item: ArticleItem?, listener: OnCollectListener?) {
            binding.apply {
                binding.tvAuthor.text = item?.author
                binding.tvTime.text = item?.niceDate
                if (!TextUtils.isEmpty(item?.envelopePic)) {
                    imageLoader.loadImage(item?.envelopePic ?: "", binding.ivImg)
                    binding.ivImg.visibility = View.VISIBLE
                } else {
                    binding.ivImg.visibility = View.GONE
                }
                binding.tvTitle.text = Html.fromHtml(item?.title)
                if (TextUtils.isEmpty(item?.desc)) {
                    binding.tvDesc.visibility = View.GONE
                    binding.tvTitle.isSingleLine = false
                } else {
                    binding.tvDesc.visibility = View.VISIBLE
                    binding.tvTitle.isSingleLine = true
                    var desc = Html.fromHtml(item?.desc).toString()
                    binding.tvDesc.text = desc
                }
                tvChapterName.text = Html.fromHtml(
                    item?.chapterName
                )
                binding.root.setOnClickListener {
                    val intent = Intent(it.context, WebViewActivity::class.java)
                    intent.putExtra("url", item?.link)
                    intent.putExtra("title", item?.title)
                    it.context.startActivity(intent)
                }
                binding.cvCollect.setChecked(
                    item?.collect == true && UserUtils.data.value == true,
                    false
                )
                binding.cvCollect.setOnClickListener {
                    if (UserUtils.data.value == false) {
                        binding.cvCollect.isChecked = false
                        val intent = Intent(it.context, LoginActivity::class.java)
                        it.context.startActivity(intent)
                        return@setOnClickListener
                    }
                    if (binding.cvCollect.isChecked) {
                        listener?.collect(item, binding.cvCollect)
                    } else {
                        listener?.uncollect(item, binding.cvCollect)
                    }
                }
            }
        }
    }

    interface OnCollectListener {
        fun collect(item: ArticleItem?, v: CollectView?)

        fun uncollect(item: ArticleItem?, v: CollectView?)
    }

}



