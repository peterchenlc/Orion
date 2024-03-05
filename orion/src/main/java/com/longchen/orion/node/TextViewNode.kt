package com.longchen.orion.node

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.longchen.orion.OrionEngine
import com.longchen.orion.utils.Utils
import org.json.JSONObject

class TextViewNode : ViewNode() {
    private lateinit var textView: TextView

    override fun createView(context: Context) : View {
        textView = TextView(context)
        return textView
    }

    override fun parseAttributes(context: Context, parent: View?, currentView: View, attributes: HashMap<String, String>,
                                 data: JSONObject?, orionEngine: OrionEngine) {
        super.parseAttributes(context, parent, textView, attributes, data, orionEngine)
        for ((name, value) in attributes) {
            if (!parseDataKey(name, value)) {
                when (name) {
                    "android:text" -> textView.text = value
                    "android:textColor" -> textView.setTextColor(parseColor(value))
                    "android:textSize" -> textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        Utils.parseDimen(context, value).toFloat())
                }
            }
        }
    }

    override fun updateData(context: Context, data: JSONObject) {
        super.updateData(context, data)
        dataKeys?.let {
            for ((key, value) in it) {
                when (key) {
                    "android:text" -> textView.text = data.optString(value)
                    "android:textColor" -> textView.setTextColor(parseColor(data.optString(value)))
                    "android:textSize" -> textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        Utils.parseDimen(context, data.optString(value)).toFloat())
                }
            }
        }
    }
}