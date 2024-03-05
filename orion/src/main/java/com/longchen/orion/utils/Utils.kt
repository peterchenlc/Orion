package com.longchen.orion.utils

import android.content.Context

object Utils {
    fun parseDimen(context: Context, value: String): Int {
        var dimen = 0
        if (value.endsWith("dp")) {
            dimen = dp2px(context, value.substring(0, value.length - 2).toFloat())
        } else if (value.endsWith("px")) {
            dimen = value.substring(0, value.length - 2).toInt()
        }
        return dimen
    }

    fun dp2px(context: Context, dp: Float): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

    fun isWrappedWithDollarSign(s: String) : Boolean {
        return s.startsWith("${'$'}{") && s.endsWith("}")
    }
}