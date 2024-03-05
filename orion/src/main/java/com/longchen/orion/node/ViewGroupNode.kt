package com.longchen.orion.node

import android.content.Context
import org.json.JSONObject

open class ViewGroupNode : ViewNode() {
    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
        children?.let {
            for (child in it) {
                child.updateData(context, data)
            }
        }
    }
}