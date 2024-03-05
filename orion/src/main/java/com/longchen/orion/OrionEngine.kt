package com.longchen.orion

import android.view.View
import dalvik.system.PathClassLoader
import java.lang.reflect.Method

class OrionEngine(val viewName: String) {
    private lateinit var clazz: Class<*>
    private lateinit var scriptObject: Any

    init {
        loadClass()
    }

    companion object {
        const val LAYOUT_FILE_NAME = "view_layout.xml"
        const val SCRIPT_FILE_NAME = "script.aar"
        const val CLASS_NAME = "com.longchen.orion.script.Script"

        private lateinit var directory: String
        private var imageLoader: ImageLoader? = null

        fun initializeOrion(directory: String, imageLoader: ImageLoader) {
            if (directory.endsWith("/")) {
                this.directory = directory
            } else {
                this.directory = "$directory/"
            }
            this.imageLoader = imageLoader
        }

        fun getDirectory(): String {
            return directory
        }

        fun getImageLoader(): ImageLoader? {
            return imageLoader
        }
    }

    private fun loadClass() {
        val classLoader = PathClassLoader("${directory}$SCRIPT_FILE_NAME", this.javaClass.classLoader)
        clazz = classLoader.loadClass(CLASS_NAME)
        scriptObject = clazz.newInstance()
    }

    fun setRootView(rootView: View) {
        val method = clazz.getMethod("setRootView", View::class.java)
        method.invoke(scriptObject, rootView)
    }

    fun getMethod(methodName: String): Method? {
        return clazz.getMethod(methodName)
    }

    fun invokeMethod(methodName: String) {
        val method = clazz.getMethod(methodName)
        method.invoke(scriptObject)
    }
}