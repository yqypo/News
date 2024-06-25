package com.example.thecat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecat.GlideImageLoader
import com.example.thecat.data.HotKeyEntity
import com.example.thecat.databinding.ItemSearchHotKeyEntityBinding
import com.example.thecat.glide.ImageLoader

/**
 * recyclerview 适配器
 */
class SearchHistoryAdapter : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {
    var list = mutableListOf<HotKeyEntity>()
    var listener:OnItemClickListener? = null
    fun setData(datas: List<HotKeyEntity>?) {
        list.clear()
        datas?.let { list.addAll(it) }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchHotKeyEntityBinding.inflate(
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
        private val binding: ItemSearchHotKeyEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val imageLoader: ImageLoader by lazy {
            GlideImageLoader(binding.root.context)
        }

        fun bind(item: HotKeyEntity?, listener: OnItemClickListener?) {
            binding.apply {
                tvKey.text = item?.name
                itemView.setOnClickListener {
                    listener?.onClickHotKeyEntityListener(item)
                }
            }
        }
    }

    interface OnItemClickListener {

      fun onClickHotKeyEntityListener(item:HotKeyEntity?)
    }

}



