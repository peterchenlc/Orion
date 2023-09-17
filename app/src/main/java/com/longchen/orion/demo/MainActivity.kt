package com.longchen.orion.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.litho.Component
import com.facebook.litho.ComponentScope
import com.facebook.litho.KComponent
import com.facebook.litho.LithoView
import com.facebook.litho.kotlin.widget.Text
import com.facebook.rendercore.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val lithoView = LithoView.create(this, MyComponent())
        setContentView(lithoView)
    }
}

class MyComponent() : KComponent() {
    override fun ComponentScope.render(): Component {
        return Text(text = "Hello, World!", textSize = 50.dp)
    }
}