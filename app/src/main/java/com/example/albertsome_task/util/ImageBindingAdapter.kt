package com.example.albertsome_task.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imageView: AppCompatImageView,url:String?){
    url?.let {
        val width = imageView.getDrawable().intrinsicWidth
        val height = imageView.getDrawable().intrinsicHeight
        Glide.with(imageView.context)
            .load(it)
            .circleCrop()
            .override(width,height)
            .into(imageView)
    }
}