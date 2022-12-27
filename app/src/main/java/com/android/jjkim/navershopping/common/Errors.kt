package com.android.jjkim.navershopping.common

import android.content.Context
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.app.service.model.ResponseBase
import com.android.jjkim.navershopping.common.utils.Utils

class Errors {

    companion object {

        fun show(context: Context, res: ResponseBase, isBack: Boolean) {
            if (context == null || (context as AppCompatActivity).isFinishing) return

            val resultMessage = res.getResultMessage().toString()

            var message: String =
                if (!TextUtils.isEmpty(resultMessage))
                    resultMessage
                else
                    context.getResources().getString(R.string.text_error_api)

            Utils.DialogShowOneBt(
                context,
                context.getResources().getString(R.string.app_name),
                message,
                context.getResources().getString(R.string.str_ok)
            ) { dialog, which ->
                if (which == Utils.MSG_OK) {
                    dialog.dismiss()
                    if (isBack) context.onBackPressed()
                }
            }
        }

        fun showExitPopup(context: Context, res: ResponseBase) {
            val resultMessage = res.getResultMessage().toString()

            var message =
                if (!TextUtils.isEmpty(resultMessage))
                    resultMessage
                else
                    context.resources.getString(R.string.dialog_network_error)

            Utils.DialogShowOneBt(
                context as AppCompatActivity,
                context.getResources().getString(R.string.app_name),
                message,
                context.getResources().getString(R.string.str_ok)
            ) { dialog, which ->
                if (which == Utils.MSG_OK) {
                    dialog.dismiss()
                    Utils.quitApp(context)
                }
            }
        }
    }
}