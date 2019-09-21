package com.emintolgahanpolat.mytoolbar

import android.graphics.*
import androidx.annotation.DrawableRes
import java.net.URL
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.math.min


/**
 * Created by emintolgahanpolat on 2019-09-17
 */

object BitmapHelper {

     fun circualBitmap(source: Bitmap): Bitmap {
        val size = min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(
            squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2.3f
        canvas.drawCircle(r, r, r, paint)

        squaredBitmap.recycle()
        return bitmap
    }
}