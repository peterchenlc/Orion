package com.longchen.orion.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.longchen.orion.ImageLoader
import com.longchen.orion.OrionEngine
import com.longchen.orion.OrionView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OrionEngine.initializeOrion("/data/data/com.longchen.orion.demo/cache",
            object : ImageLoader {
                override fun load(imageView: ImageView, imageUrl: String) {
                    Glide.with(this@MainActivity)
                        .load(imageUrl)
                        .into(imageView)
                }
        })

        val data = JSONObject()
        data.put("name", "123456")
        data.put("icon_url", "https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF")
        val array = JSONArray()
        val item1 = JSONObject()
        item1.put("name", "a")
        item1.put("icon_url", "https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF")
        array.put(item1)
        val item2 = JSONObject()
        item2.put("name", "b")
        array.put(item2)
        val item3 = JSONObject()
        item3.put("name", "c")
        array.put(item3)
        val item4 = JSONObject()
        item4.put("name", "d")
        array.put(item4)
        val item5 = JSONObject()
        item5.put("name", "e")
        array.put(item5)
        val item6 = JSONObject()
        item6.put("name", "f")
        array.put(item6)
        data.put("items", array)
        val rootView = OrionView.create(this,
            "test_view", data)
        setContentView(rootView)
//        setContentView(R.layout.activity_main)
    }
}