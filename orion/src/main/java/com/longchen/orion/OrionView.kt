package com.longchen.orion

import android.content.Context
import android.view.View
import com.longchen.orion.parser.LayoutParser
import org.json.JSONObject

class OrionView {
    companion object {
        fun create(context: Context, viewName: String, data: JSONObject): View {
            val orionEngine = OrionEngine(viewName)
            val rootView = LayoutParser.parse(
                context,
                "${OrionEngine.getDirectory()}$viewName/${OrionEngine.LAYOUT_FILE_NAME}",
                data,
                orionEngine).first
            orionEngine.setRootView(rootView!!)
            return rootView
        }
    }
}