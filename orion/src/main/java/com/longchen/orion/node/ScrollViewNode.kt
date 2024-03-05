package com.longchen.orion.node

import android.content.Context
import android.view.View
import android.widget.ScrollView
import com.longchen.orion.OrionEngine
import org.json.JSONObject

class ScrollViewNode : FrameLayoutNode() {
    private lateinit var scrollView: ScrollView

    override fun createView(context: Context) : View {
        scrollView = ScrollView(context)
        return scrollView
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, scrollView, attributes, data, orionEngine)
    }

    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
    }
}