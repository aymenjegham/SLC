package com.aymen.slc.global.utils

import android.graphics.*
import androidx.annotation.ColorInt
import com.squareup.picasso.Transformation
import kotlin.math.min


private const val CIRCULAR_TRANSFORMATION_KEY = "circular_transformation"


/**
 * @param borderWidth in pixel
 * @param color ColorInt
 *
 * */
class PicassoCircularTransformation(@ColorInt private val color: Int, private val borderWidth: Float) :
    Transformation {

    override fun key() = CIRCULAR_TRANSFORMATION_KEY


    override fun transform(source: Bitmap?): Bitmap? {
        return if (source == null || source.isRecycled) {
            null
        } else {

            val width = source.width + borderWidth
            val height = source.height + borderWidth
            val radius = min(height, width) / 2

            val result = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
            val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val canvas = Canvas(result)

            paint.shader = shader
            canvas.drawCircle(width / 2, height / 2, radius, paint)

            paint.shader = null
            paint.style = Paint.Style.STROKE
            paint.color = color
            paint.strokeWidth = borderWidth
            canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint)


            if (result != source) {
                source.recycle()
            }

            result
        }

    }


}