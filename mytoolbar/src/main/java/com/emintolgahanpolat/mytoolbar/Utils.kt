package com.emintolgahanpolat.mytoolbar

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.core.view.iterator


/**
 * Created by emintolgahanpolat on 2019-09-17
 */

fun dpToPx(context: Context, dp: Int): Int {
    val px = Math.round(dp * getPixelScaleFactor(context))
    return px
}

fun pxToDp(context: Context, px: Int): Int {
    val dp = Math.round(px / getPixelScaleFactor(context))
    return dp
}

private fun getPixelScaleFactor(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
}

private fun dp2px(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

