package com.android.jjkim.navershopping.app.service

import com.android.jjkim.navershopping.common.LogUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetryableCallback<T>(private val call: Call<T>, totalRetries: Int) : Callback<T> {
    private var totalRetries = 3
    private var retryCount = 0

    init {
        this.totalRetries = totalRetries
    }

    companion object {
        private val TAG = RetryableCallback::class.java.simpleName
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!APIHelper.isCallSuccess(response)) if (retryCount++ < totalRetries) {
            LogUtil.v(TAG, "Retrying API Call -  ($retryCount / $totalRetries)")
            retry()
        } else onFinalResponse(call, response) else onFinalResponse(call, response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t?.message?.let { LogUtil.e(TAG, it) }
        if (retryCount++ < totalRetries) {
            LogUtil.v(TAG, "Retrying API Call -  ($retryCount / $totalRetries)")
            retry()
        } else onFinalFailure(call, t)
    }

    private fun retry() {
        call.clone().enqueue(this)
    }

    open fun onFinalResponse(call: Call<T>?, response: Response<T>?) {}
    open fun onFinalFailure(call: Call<T>?, t: Throwable?) {}
}