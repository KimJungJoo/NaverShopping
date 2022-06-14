package com.android.jjkim.navershopping.service.model

import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class ResponseBase : Serializable {

    @SerializedName("retcode")
    private var resultCode: String = RESPONSE_OK

    @SerializedName("retmsg")
    private var retmsg: String = ""

    companion object {
        //api response return code
        const val RESPONSE_OK = "OK"
        const val RESPONSE_FAIL = "FAIL"
    }

    fun getResultMessage() {
        if (TextUtils.isEmpty(retmsg)) ""
        else retmsg.replace("\\n", "\n")
    }

    constructor(code: String?, msg: String?) {
        code?.let { resultCode = it }
        msg?.let { retmsg = it }
    }


    val isSuccess: Boolean
        get() = true
//        get() = resultCode == RESPONSE_OK

    override fun toString(): String {
        return "ResponseBase{" +
                "retcode=" + resultCode +
                ", retmsg='" + retmsg + '\'' +
                '}'
    }
}