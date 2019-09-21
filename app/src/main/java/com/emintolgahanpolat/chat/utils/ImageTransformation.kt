package com.emintolgahanpolat.chat.utils

import android.graphics.*

import com.squareup.picasso.Transformation
import android.graphics.Bitmap
import com.emintolgahanpolat.chat.utils.ImageTransformation.Transforms.*

/**
 * Created by emintolgahanpolat on 2019-09-17
 */

class ImageTransformation (private val transform:Transforms,private val value: Float = 0.toFloat()) : Transformation {


    enum class Transforms{
        CircleTransform,RadiusTransform,AplaTransform
    }
    private fun transformation(source: Bitmap):Bitmap{
        when(transform){
            CircleTransform ->{
                val size = Math.min(source.width, source.height)
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

                val r = size / 2f
                canvas.drawCircle(r, r, r, paint)

                squaredBitmap.recycle()
                return bitmap
            }
            RadiusTransform ->{
                val paint = Paint()
                paint.isAntiAlias = true
                paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

                val output = Bitmap.createBitmap(source.width, source.height, source.config)
                val canvas = Canvas(output)
                canvas.drawRoundRect(
                    RectF(
                        0F,
                        0F,
                        (source.width).toFloat(),
                        (source.height).toFloat()
                    ), value.toFloat(), value.toFloat(), paint
                )

                if (source != output) {
                    source.recycle()
                }

                return output
            }
            AplaTransform->{
                val width = source.width
                val height = source.height

                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

                val canvas = Canvas(bitmap)
                val colors = intArrayOf(Color.parseColor("#05000000"), Color.parseColor("#22000000"), Color.parseColor("#95000000"))
                val gradient = LinearGradient(0f, 0f, 0f, height.toFloat(), colors, null, Shader.TileMode.CLAMP)
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.shader = ComposeShader(BitmapShader(source, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP), gradient, PorterDuff.Mode.SRC_OVER)

                canvas.drawRect(Rect(0, 0, width, height), paint)
                source.recycle()

                return bitmap
            }
        }
        return source
    }



    override fun transform(source: Bitmap): Bitmap {
        return transformation(source)
    }

    override fun key(): String {
        return when(transform){
            CircleTransform ->{
                "CircleTrasnform"
            }
            RadiusTransform ->{
                "CornerRadius"
            }
            AplaTransform->{
                "AplaTransform"
            }

        }
    }
}