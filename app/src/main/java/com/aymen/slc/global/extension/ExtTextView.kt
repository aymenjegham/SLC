package com.aymen.slc.global.extension

import android.graphics.Color
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.aymen.slc.R

@BindingAdapter("onClickSpan", "clickableText", "prefixText", requireAll = false)
fun setClickableText(
    textView: TextView,
    onTextClick: View.OnClickListener?,
    clickableText: String?,
    prefixText: String?
) {

    val clickableText = SpannableString(clickableText).also {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(v: View) {
                onTextClick?.onClick(v)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        it.setSpan(
            clickableSpan,
            0,
            clickableText?.length ?: 0,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        it.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    textView.context,
                    R.color.secondary
                )
            ),
            0,
            clickableText?.length ?: 0,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }.let { SpannableStringBuilder().append(prefixText).append(it) }

    textView.apply {
        text = clickableText
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }

}


@BindingAdapter("html")
fun parseHtml(textView: TextView, text: String?) {
    textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(text)
    }
}

fun String?.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches() && !this.isNullOrBlank()
}