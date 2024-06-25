package com.example.thecat.ui.adapter

import android.content.Intent
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecat.GlideImageLoader
import com.example.thecat.glide.ImageLoader
import com.example.thecat.data.CollectionArticleItem
import com.example.thecat.databinding.ItemArticleBinding
import com.example.thecat.ui.activity.WebViewActivity
import com.example.thecat.ui.view.CollectView

/**
 * recyclerview 适配器
 */
class CollectionArticleAdapter : RecyclerView.Adapter<CollectionArticleAdapter.ViewHolder>() {
    var list = mutableListOf<CollectionArticleItem>()
    var listener: OnCollectListener? = null

    fun setData(datas: List<CollectionArticleItem>?) {
        list.clear()
        datas?.let { list.addAll(it) }
        notifyDataSetChanged()
    }
    fun addData(datas: List<CollectionArticleItem>?){
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
        holder.bind(list[holder.adapterPosition],listener)
    }

    fun removeData(item: CollectionArticleItem?, adapterPosition: Int) {
        list.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    class ViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val imageLoader: ImageLoader by lazy {
            GlideImageLoader(binding.root.context)
        }

        fun bind(item: CollectionArticleItem?, listener: OnCollectListener?) {
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
                    intent.putExtra("url",item?.link)
                    intent.putExtra("title",item?.title)
                    it.context.startActivity(intent)
                }
                binding.cvCollect.setChecked(true,false)
                binding.cvCollect.setOnClickListener {
                    if (!binding.cvCollect.isChecked) {
                        listener?.uncollect(item,adapterPosition, binding.cvCollect)
                    }
                }
            }
        }
    }
    interface OnCollectListener {
        fun uncollect(item: CollectionArticleItem?, adapterPosition: Int, v: CollectView?)
    }
}



