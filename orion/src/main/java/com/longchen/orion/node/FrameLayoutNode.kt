package com.longchen.orion.node

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.longchen.orion.OrionEngine
import org.json.JSONObject

open class FrameLayoutNode : ViewGroupNode() {
    private lateinit var frameLayout: FrameLayout

    override fun createView(context: Context) : View {
        frameLayout = FrameLayout(context)
        return frameLayout
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, frameLayout, attributes, data, orionEngine)
    }

    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
    }
}