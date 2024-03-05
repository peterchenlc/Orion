package com.longchen.orion.parser

import android.content.Context
import android.util.Log
import android.util.Xml
import android.view.View
import android.view.ViewGroup
import com.longchen.orion.OrionEngine
import com.longchen.orion.node.FrameLayoutNode
import com.longchen.orion.node.ImageViewNode
import com.longchen.orion.node.LinearLayoutNode
import com.longchen.orion.node.RecyclerViewNode
import com.longchen.orion.node.RelativeLayoutNode
import com.longchen.orion.node.ScrollViewNode
import com.longchen.orion.node.TextViewNode
import com.longchen.orion.node.ViewNode
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileInputStream
import java.util.Stack

object LayoutParser {

    private const val TAG = "LayoutParser"

    fun parse(context: Context, layoutFilePath: String, data: JSONObject?, orionEngine: OrionEngine): Pair<View?, ViewNode?> {
        val file = File(layoutFilePath)
        val fis = FileInputStream(file)
        var rootNode: ViewNode? = null
        var rootView: View? = null
//        val inputStream = context.resources.openRawResource(R.raw.test_layout)
        fis.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            val nodeStack = Stack<ViewNode>()
            val viewStack = Stack<View>()
            var eventType = parser.eventType
            val attributes = HashMap<String, String>()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "start_tag = ${parser.name}")
                        attributes.clear()
                        for (i in 0 until parser.attributeCount) {
                            val name = parser.getAttributeName(i)
                            val value = parser.getAttributeValue(i)
                            attributes[name] = value
                            Log.d(TAG, "name = $name, value = $value")
                        }
                        val currentNode = when (parser.name) {
                            "View" -> ViewNode()
                            "LinearLayout" -> LinearLayoutNode()
                            "RelativeLayout" -> RelativeLayoutNode()
                            "FrameLayout" -> FrameLayoutNode()
                            "ScrollView" -> ScrollViewNode()
                            "androidx.recyclerview.widget.RecyclerView" -> RecyclerViewNode()
                            "ImageView" -> ImageViewNode()
                            "TextView" -> TextViewNode()
                            else -> ViewNode()
                        }
                        val currentView = currentNode.createView(context)
                        currentNode.parseAttributes(context,
                            if (viewStack.isEmpty()) null else viewStack.peek(),
                            currentView, attributes, data, orionEngine)
                        data?.let { currentNode.updateData(context, data) }

                        if (rootNode == null) {
                            rootNode = currentNode
                        }
                        if (rootView == null) {
                            rootView = currentView
                        }
                        if (!nodeStack.empty()) {
                            if (nodeStack.peek().children == null) {
                                nodeStack.peek().children = ArrayList()
                            }
                            nodeStack.peek().children!!.add(currentNode)
                        }
                        if (!viewStack.empty()) {
                            (viewStack.peek() as ViewGroup).addView(currentView)
                        }
                        nodeStack.push(currentNode)
                        viewStack.push(currentView)
                    }
                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "end_tag = ${parser.name}")
                        nodeStack.pop()
                        viewStack.pop()
                    }
                }
                eventType = parser.next()
            }
        }

        return Pair(rootView, rootNode)
    }
}