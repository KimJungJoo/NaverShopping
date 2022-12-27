package com.android.jjkim.navershopping.common.utils

import android.app.Activity
import android.view.Window
import androidx.fragment.app.Fragment

class ScreenLighter(window: Window?) {
    private var mWindow: Window? = null
    private var currentBrightness = 0f

    init {
        mWindow = window
        currentBrightness = mWindow!!.attributes.screenBrightness
    }

    fun enlighten() {
        setBrightness(1f)
    }

    fun restore() {
        setBrightness(currentBrightness)
    }

    private fun setBrightness(value: Float) {
        val wlp = mWindow!!.attributes
        wlp.screenBrightness = value
        mWindow!!.attributes = wlp
    }
}