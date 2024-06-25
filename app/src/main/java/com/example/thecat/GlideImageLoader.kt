package com.example.thecat

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.thecat.glide.ImageLoader
import jp.wasabeef.glide.transformations.CropCircleTransformation

class GlideImageLoader(private val context: Context)
    : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .override(350,263)
            .into(imageView)
    }

    override fun loadCircleImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }

    override fun loadCircleImage(imageRes: Int, imageView: ImageView) {
        Glide.with(context)
            .load(imageRes)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }
}