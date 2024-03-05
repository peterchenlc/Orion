package com.longchen.orion.node

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.longchen.orion.OrionEngine
import org.json.JSONObject

class LinearLayoutNode : ViewGroupNode() {
    private lateinit var linearLayout: LinearLayout

    override fun createView(context: Context) : View {
        linearLayout = LinearLayout(context)
        return linearLayout
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, linearLayout, attributes, data, orionEngine)
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:orientation" -> {
                        when (value) {
                            "vertical" -> linearLayout.orientation = LinearLayout.VERTICAL
                            "horizontal" -> linearLayout.orientation = LinearLayout.HORIZONTAL
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