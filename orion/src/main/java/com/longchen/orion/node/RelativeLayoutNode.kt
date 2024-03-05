package com.longchen.orion.node

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.longchen.orion.OrionEngine
import org.json.JSONObject

class RelativeLayoutNode : ViewGroupNode() {
    private lateinit var relativeLayout: RelativeLayout

    override fun createView(context: Context) : View {
        relativeLayout = RelativeLayout(context)
        return relativeLayout
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, relativeLayout, attributes, data, orionEngine)
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:gravity" -> {
                        when (value) {
                            "center_vertical" -> relativeLayout.gravity = Gravity.CENTER_VERTICAL
                            "center_horizontal" -> relativeLayout.gravity = Gravity.CENTER_HORIZONTAL
                            "center" -> relativeLayout.gravity = Gravity.CENTER
                        }
                    }
                }
            }
        }
    }

    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
    }
}