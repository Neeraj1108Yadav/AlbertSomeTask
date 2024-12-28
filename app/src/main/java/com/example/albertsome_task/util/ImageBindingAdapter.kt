package com.example.albertsome_task.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil3.load

@BindingAdapter("imageUrl")
fun bindImage(imageView: AppCompatImageView,url:String?){
    url?.let {
        imageView.load(it) {

        }
    }
}