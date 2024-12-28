package com.example.albertsome_task.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil3.load
import coil3.request.transformations
import coil3.transform.CircleCropTransformation

@BindingAdapter("imageUrl","isCircle", requireAll = false)
fun bindImage(imageView: AppCompatImageView,url:String?,isCircle: Boolean?){
    url?.let {
        imageView.load(it) {
            val width = imageView.getDrawable().intrinsicWidth
            val height = imageView.getDrawable().intrinsicHeight
            size(width,height)
            if(isCircle == true){
                transformations(CircleCropTransformation())
            }
        }
    }
}