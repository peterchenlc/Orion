package com.longchen.orion.node

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.longchen.orion.OrionEngine
import com.longchen.orion.view.RecyclerViewAdapter
import org.json.JSONObject

class RecyclerViewNode : ViewGroupNode() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private var dataKey = "items"

    override fun createView(context: Context) : View {
        recyclerView = RecyclerView(context)
        return recyclerView
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, recyclerView, attributes, data, orionEngine)
        var orientation = "vertical"
        var layoutManager = "linearLayoutManager"
        var reverseLayout = false
        var childLayout = ""
        var spanCount = 0
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:orientation" -> orientation = value
                    "android:layoutManager" -> layoutManager = value
                    "android:reverseLayout" -> reverseLayout = value.toBoolean()
                    "android:childLayout" -> childLayout = value
                    "android:spanCount" -> spanCount = value.toInt()
                    "android:data_key" -> dataKey = value
                }
            }
        }
        when (layoutManager) {
            "linearLayoutManager" -> recyclerView.layoutManager = LinearLayoutManager(context,
                if (orientation == "vertical") RecyclerView.VERTICAL else RecyclerView.HORIZONTAL,
                reverseLayout)
            "gridLayoutManager" -> recyclerView.layoutManager = GridLayoutManager(context, spanCount,
                if (orientation == "vertical") RecyclerView.VERTICAL else RecyclerView.HORIZONTAL,
                reverseLayout)
        }
        adapter = RecyclerViewAdapter(context, childLayout, data?.optJSONArray(dataKey), orionEngine)
        recyclerView.adapter = adapter
    }

    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
        data.optJSONArray(dataKey)?.let { adapter.updateData(it) }
    }
}