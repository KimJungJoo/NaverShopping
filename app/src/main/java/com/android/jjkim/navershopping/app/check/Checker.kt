package com.android.jjkim.navershopping.app.check

open class Checker {
    protected lateinit var listener: CheckListener

    interface CheckListener {
        fun onSuccess()
        fun onFail()
    }

    fun setCheckListener(listener: CheckListener) {
        this.listener = listener
    }
}