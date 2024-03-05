package com.longchen.orion.view

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longchen.orion.OrionEngine
import com.longchen.orion.parser.LayoutParser
import org.json.JSONArray

class RecyclerViewAdapter(val context: Context, val layoutFileName: String,
                          var data: JSONArray?, val orionEngine: OrionEngine) : RecyclerView.Adapter<OrionViewHolder>() {
    companion object {
        private const val KEY_ITEMS = "items"
    }

    override fun getItemCount(): Int {
        return data?.length() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrionViewHolder {
        val pair = LayoutParser.parse(context,
            "${OrionEngine.getDirectory()}${orionEngine.viewName}/$layoutFileName",
            null, orionEngine)
        return OrionViewHolder(pair.first!!, pair.second!!)
    }

    override fun onBindViewHolder(holder: OrionViewHolder, position: Int) {
        data?.let {
            val itemData = it.getJSONObject(position)
            holder.rootNode.updateData(context, itemData)
        }
    }

    fun updateData(data: JSONArray) {
        this.data = data
        notifyDataSetChanged()
    }
}