package com.longchen.orion

import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, imageUrl: String)
}