package com.longchen.orion.node

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.longchen.orion.OrionEngine
import org.json.JSONObject

class ImageViewNode : ViewNode() {
    companion object {
        private const val PREFIX_DRAWABLE = "@drawable/"
    }

    private lateinit var imageView: ImageView

    override fun createView(context: Context) : View {
        imageView = ImageView(context)
        return imageView
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, imageView, attributes, data, orionEngine)
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:src" -> {
                        if (value.startsWith(PREFIX_DRAWABLE)) {
                            val resId = context.resources.getIdentifier(value.substring(
                                PREFIX_DRAWABLE.length),
                                "drawable", context.packageName)
                            imageView.setImageResource(resId)
                        }
                    }
                    "android:scaleType" -> {
                        when (value) {
                            "matrix" -> imageView.scaleType = ImageView.ScaleType.MATRIX
                            "fitXY" -> imageView.scaleType = ImageView.ScaleType.FIT_XY
                            "fitStart" -> imageView.scaleType = ImageView.ScaleType.FIT_START
                            "fitCenter" -> imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                            "fitEnd" -> imageView.scaleType = ImageView.ScaleType.FIT_END
                            "center" -> imageView.scaleType = ImageView.ScaleType.CENTER
                            "centerCrop" -> imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            "centerInside" -> imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                        }
                    }
                }
            }
        }
    }

    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
        dataKeys?.let {
            for ((key, value) in it) {
                when (key) {
                    "android:src" -> {
                        val imageUrl = data.optString(value)
                        OrionEngine.getImageLoader()?.load(imageView, imageUrl)
                    }
                }
            }
        }
    }
}