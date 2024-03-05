package com.longchen.orion.node

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.children
import com.longchen.orion.OrionEngine
import com.longchen.orion.R
import com.longchen.orion.utils.Utils
import org.json.JSONObject

open class ViewNode {
    companion object {
        private const val PREFIX_ADD_ID = "@+id/"
        private const val PREFIX_ID = "@id/"
        private const val PREFIX_COLOR = "@color/"
    }

    private lateinit var view: View
    var children: ArrayList<ViewNode>? = null
    var dataKeys: HashMap<String, String>? = null

    open fun createView(context: Context) : View {
        view = View(context)
        return view
    }

    private fun parseWidthOrHeight(context: Context, value: String): Int {
        val dimen = when (value) {
            "match_parent" -> ViewGroup.LayoutParams.MATCH_PARENT
            "wrap_content" -> ViewGroup.LayoutParams.WRAP_CONTENT
            else -> Utils.parseDimen(context, value)
        }
        return dimen
    }

    fun parseColor(value: String): Int {
        var color = 0
        if (value.startsWith(PREFIX_COLOR)) {
            when (value.substring(PREFIX_COLOR.length)) {
                "black" -> color = Color.BLACK
                "white" -> color = Color.WHITE
                "red" -> color = Color.RED
                "green" -> color = Color.GREEN
                "blue" -> color = Color.BLUE
            }
        } else if (value.startsWith("#")) {
            color = Color.parseColor(value)
        }
        return color
    }

    fun parseDataKey(name: String, value: String): Boolean {
        if (Utils.isWrappedWithDollarSign(value)) {
            if (dataKeys == null) {
                dataKeys = HashMap()
            }
            dataKeys!![name] = value.substring(2, value.length - 1)
            return true
        }
        return false
    }

    open fun parseAttributes(context: Context, parent: View?, currentView: View,
                             attributes: HashMap<String, String>,
                             data: JSONObject?, orionEngine: OrionEngine) {
        var width = ViewGroup.LayoutParams.MATCH_PARENT
        var height = ViewGroup.LayoutParams.MATCH_PARENT
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:layout_width" -> width = parseWidthOrHeight(context, value)
                    "android:layout_height" -> height = parseWidthOrHeight(context, value)
                }
            }
        }
        val lp = if (parent is RelativeLayout) {
            RelativeLayout.LayoutParams(width, height)
        } else {
            ViewGroup.MarginLayoutParams(width, height)
        }
        var paddingLeft = 0
        var paddingTop = 0
        var paddingRight = 0
        var paddingBottom = 0
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:id" -> {
                        currentView.id = View.generateViewId()
                        if (value.startsWith(PREFIX_ADD_ID)) {
                            currentView.setTag(R.id.orion_view_id_tag_id, value.substring(PREFIX_ADD_ID.length))
                        }
                    }
                    "android:background" -> {
                        currentView.setBackgroundColor(parseColor(value))
                    }
                    "android:layout_marginLeft" -> {
                        lp.leftMargin = Utils.parseDimen(context, value)
                    }
                    "android:layout_marginRight" -> {
                        lp.rightMargin = Utils.parseDimen(context, value)
                    }
                    "android:layout_marginTop" -> {
                        lp.topMargin = Utils.parseDimen(context, value)
                    }
                    "android:layout_marginBottom" -> {
                        lp.bottomMargin = Utils.parseDimen(context, value)
                    }
                    "android:padding" -> {
                        paddingLeft = Utils.parseDimen(context, value)
                        paddingTop = paddingLeft
                        paddingRight = paddingLeft
                        paddingBottom = paddingLeft
                    }
                    "android:paddingLeft" -> {
                        paddingLeft = Utils.parseDimen(context, value)
                    }
                    "android:paddingTop" -> {
                        paddingTop = Utils.parseDimen(context, value)
                    }
                    "android:paddingRight" -> {
                        paddingRight = Utils.parseDimen(context, value)
                    }
                    "android:paddingBottom" -> {
                        paddingBottom = Utils.parseDimen(context, value)
                    }
                    "android:layout_centerVertical" -> {
                        if (parent is RelativeLayout && value == "true") {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_VERTICAL)
                        }
                    }
                    "android:layout_centerHorizontal" -> {
                        if (parent is RelativeLayout && value == "true") {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_HORIZONTAL)
                        }
                    }
                    "android:layout_centerInParent" -> {
                        if (parent is RelativeLayout && value == "true") {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT)
                        }
                    }
                    "android:layout_toRightOf" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.RIGHT_OF, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_toLeftOf" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.LEFT_OF, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_above" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ABOVE, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_below" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.BELOW, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_alignLeft" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_LEFT, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_alignTop" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_TOP, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_alignRight" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_RIGHT, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_alignBottom" -> {
                        if (parent is RelativeLayout) {
                            val idStr = value.substring(PREFIX_ID.length)
                            for (child in parent.children) {
                                if (idStr == (child.getTag(R.id.orion_view_id_tag_id) as String)) {
                                    (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_BOTTOM, child.id)
                                    break
                                }
                            }
                        }
                    }
                    "android:layout_alignParentLeft" -> {
                        if ((parent is RelativeLayout) && (value == "true")) {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_LEFT, parent.id)
                        }
                    }
                    "android:layout_alignParentTop" -> {
                        if ((parent is RelativeLayout) && (value == "true")) {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_TOP, parent.id)
                        }
                    }
                    "android:layout_alignParentRight" -> {
                        if ((parent is RelativeLayout) && (value == "true")) {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, parent.id)
                        }
                    }
                    "android:layout_alignParentBottom" -> {
                        if ((parent is RelativeLayout) && (value == "true")) {
                            (lp as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, parent.id)
                        }
                    }
                    "android:onClick" -> {
                        currentView.setOnClickListener {
                            orionEngine.invokeMethod(value)
                        }
                    }
                }
            }
        }
        currentView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
        currentView.layoutParams = lp
    }

    open fun updateData(context: Context, data: JSONObject) {
    }
}