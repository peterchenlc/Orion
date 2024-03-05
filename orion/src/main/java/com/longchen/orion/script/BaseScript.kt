package com.longchen.orion.script

import android.view.View
import android.view.ViewGroup
import com.longchen.orion.R

open class BaseScript {
    private lateinit var rootView: View

    companion object {
        private const val TAG = "Script"

        fun findViewById(view: View, id: String): View? {
            val tag: Any? = view.getTag(R.id.orion_view_id_tag_id)
            if (tag != null) {
                val tagId: String = tag as String
                if (id == tagId) {
                    return view
                }
            }

            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val child = findViewById(view.getChildAt(i), id)
                    if (child != null) {
                        return child
                    }
                }
            }
            return null
        }
    }

    fun setRootView(rootView: View) {
        this.rootView = rootView
    }

    fun getRootView(): View {
        return rootView
    }

}