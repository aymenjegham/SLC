package com.aymen.slc.ui.dialog.standard

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.aymen.slc.databinding.DialogStandardBinding
import com.aymen.slc.ui.data.DialogData
import com.aymen.slc.ui.data.DialogType


class StandardDialog(
    context: Context,
    private val data: DialogData,
    private val cancelable: Boolean,
    private val dismissAction: (() -> Unit)? = null
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DialogStandardBinding
            .inflate(layoutInflater, null, false)
            .also(::bind)

        setupDismiss()

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        setCanceledOnTouchOutside(false)
        setCancelable(cancelable)
    }

    private fun bind(binding: DialogStandardBinding) {
        binding.apply {
            setContentView(root)
            dialogData = data
            setOkAction {
                dismiss()
                data.okAction?.invoke()
            }

            if (data.dialogType == DialogType.CHOOSE) {
                setCancelAction {
                    dismiss()
                    data.cancelAction?.invoke()
                }
            }
        }

    }

    private fun setupDismiss() {
        setOnDismissListener {
            dismissAction?.invoke()

        }
    }
}