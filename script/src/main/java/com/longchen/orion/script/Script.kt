package com.longchen.orion.script

import android.util.Log
import android.widget.TextView

class Script : BaseScript() {
    private var count = 1

    companion object {
        private const val TAG = "Script"
    }

    fun increase() {
        Log.i(TAG, "call increase()")
        val textView = findViewById(getRootView(), "text_view") as TextView?
        ++count
        textView?.text = count.toString()
    }
}