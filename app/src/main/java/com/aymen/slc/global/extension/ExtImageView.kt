package com.aymen.slc.global.extension

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.aymen.slc.global.utils.PicassoCircularTransformation
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

private var drawableImage: Drawable? = null

@BindingAdapter(value = ["imageUrl", "placeholder", "picasso"], requireAll = true)
fun setImageUrl(imageView: ImageView, imageUrl: String?, placeHolder: Drawable, picasso: Picasso) {
    setImage(
        imageView,
        imageUrl,
        placeHolder,
        picasso
    )
}

@BindingAdapter(value = ["imagePath", "holder"], requireAll = true)
fun setImageUri(imageView: ImageView, imagePath: String?, placeHolder: Drawable) {
    if (imagePath.isNullOrEmpty())
        imageView.setImageDrawable(placeHolder)
    else
        imageView.setImageURI(Uri.parse(imagePath))
}


@BindingAdapter(
    value = ["roundedImageUrl", "roundedImagePlaceholder", "picasso", "borderWidth", "borderColor"],
    requireAll = true
)
fun setRoundedImageUri(
    imageView: ImageView,
    imageUrl: String?,
    placeHolder: Drawable,
    picasso: Picasso,
    borderWidth: Float,
    borderColor: Int
) {
    setImage(
        imageView,
        imageUrl,
        placeHolder,
        picasso,
        PicassoCircularTransformation(
            borderColor,
            imageView.context.dpToPx(borderWidth)
        )
    )
}

private fun setImage(
    imageView: ImageView,
    imageUrl: String?,
    placeHolder: Drawable,
    picasso: Picasso,
    transformation: Transformation? = null
) {

    imageUrl?.let {
        if (imageUrl.isNullOrEmpty()) {
            imageView.setImageDrawable(placeHolder)
        } else {

            var rc = picasso.load(it).fit().placeholder(placeHolder)

            rc = when (imageView.scaleType) {
                ImageView.ScaleType.CENTER_CROP -> rc.centerCrop()
                ImageView.ScaleType.CENTER_INSIDE -> rc.centerInside()
                else -> rc
            }

            transformation?.let { rc.transform(transformation) }

            rc.into(imageView)
        }
    }

}

@BindingAdapter("srcResourceId")
fun getImageFromResource(imageView: ImageView, @DrawableRes id: Int) =
    imageView.setImageResource(id)

@BindingAdapter("backgroundTintColor")
fun setBackgroundColor(imageView: ImageView, @ColorInt color: Int) {
    (imageView.background as LayerDrawable).run {
        val imageViewBackground: GradientDrawable =
            (findDrawableByLayerId(getId(0)) as GradientDrawable)
                .apply {
                    mutate()
                }
        imageViewBackground.setColor(color)
        this
    }
}

@BindingAdapter("srcTintColor")
fun setColor(imageView: ImageView, @ColorInt color: Int) =
    ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color))

fun getImageFromResource(materialButton: MaterialButton, @DrawableRes id: Int) =
    materialButton.setIconResource(id)
