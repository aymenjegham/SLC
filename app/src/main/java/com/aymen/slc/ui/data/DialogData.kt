package com.aymen.slc.ui.data

import android.graphics.drawable.Drawable


class DialogData private constructor(

        val title: String? = null,
        val message: String,
        val ok: String? = null,
        val cancel: String? = null,
        val okAction: (() -> Unit)? = null,
        val cancelAction: (() -> Unit)? = null,
        val dialogType: DialogType,
        val drawable : Drawable ? = null,
        val cancelable: Boolean = false
) {

    companion object {

        fun build(
                title: String?,
                message: String,
                ok: String?,
                cancel: String? = null,
                okAction: (() -> Unit)?,
                cancelAction: (() -> Unit)? = null,
                dialogType: DialogType,
                drawable : Drawable ? = null,
                cancelable: Boolean = false
        ) = DialogData(title, message, ok, cancel, okAction, cancelAction,dialogType,drawable,cancelable)

    }
}