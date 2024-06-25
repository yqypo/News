package com.example.thecat.glide

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageUrl:String, imageView: ImageView)
    fun loadCircleImage(imageUrl:String, imageView: ImageView)
    fun loadCircleImage(imageRes:Int, imageView: ImageView)
}