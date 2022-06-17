package com.android.jjkim.navershopping.app.service.model

/**
 * Description:
 *
 * retrofit errorBody 에러 처리
 * RetrofitErrorUtils
 */
class ResponseError(code: String?, msg: String?) : ResponseBase(code, msg) {

    var resultCode: String
        get() = resultCode
        set(resultCode) { this.resultCode = resultCode }

    var resultMessage: String
        get() = resultMessage
        set(resultMessage) { this.resultMessage = resultMessage }


    override fun toString(): String {
        return "ResponseError{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}'
    }
}