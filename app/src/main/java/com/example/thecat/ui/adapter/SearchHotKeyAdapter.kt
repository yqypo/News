package com.example.thecat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecat.GlideImageLoader
import com.example.thecat.data.HotKey
import com.example.thecat.databinding.ItemSearchHotKeyBinding
import com.example.thecat.glide.ImageLoader

/**
 * recyclerview 适配器
 */
class SearchHotKeyAdapter : RecyclerView.Adapter<SearchHotKeyAdapter.ViewHolder>() {
    var list = mutableListOf<HotKey>()
    var listener:OnItemClickListener? = null
    fun setData(datas: List<HotKey>?) {
        list.clear()
        datas?.let { list.addAll(it) }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchHotKeyBinding.inflate(
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
        private val binding: ItemSearchHotKeyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val imageLoader: ImageLoader by lazy {
            GlideImageLoader(binding.root.context)
        }

        fun bind(item: HotKey?, listener: OnItemClickListener?) {
            binding.apply {
                tvKey.text = item?.name
                itemView.setOnClickListener {
                    listener?.onClickHotKeyListener(item)
                }
            }
        }
    }

    interface OnItemClickListener {

      fun onClickHotKeyListener(item:HotKey?)
    }

}



