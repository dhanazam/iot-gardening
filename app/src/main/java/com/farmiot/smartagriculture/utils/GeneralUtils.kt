package com.farmiot.smartagriculture.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.farmiot.smartagriculture.databinding.DialogLoadingBinding

object GeneralUtils {
    fun Context.loadingDialog(onShow: (dialog: Dialog) -> Unit) {
        var bindingView = DialogLoadingBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(bindingView.root)
        dialog.show()
        onShow(dialog)
    }
}