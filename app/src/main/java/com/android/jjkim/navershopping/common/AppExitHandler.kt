/*
 * KT GoodPay version 1.0
 *
 * Copyright ⓒ 2019 kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in
 * compliance with license agreement with kt corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of kt corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.android.jjkim.navershopping.common

import android.content.Context
import android.widget.Toast
import com.android.jjkim.navershopping.R


class AppExitHandler(private var mContext: Context?) {
    companion object {
        private const val DEFAULT_DURATION = 3000
    }

    private var mBackPressTime: Long = 0
    private var mToast: Toast? = null
    private var mDuration = DEFAULT_DURATION

    fun setDuration(duration: Int) {
        mDuration = duration
    }

    fun isBackPressed(): Boolean {
        if (System.currentTimeMillis() > mBackPressTime + mDuration) {
            mBackPressTime = System.currentTimeMillis()
            showExitGuide()
            return true
        }
        if (System.currentTimeMillis() <= mBackPressTime + mDuration) {
            mToast!!.cancel()
        }
        return false
    }

    fun showExitGuide() {
        mToast = Toast.makeText(
            mContext,
            mContext!!.getString(R.string.app_exit_guide),
            Toast.LENGTH_SHORT
        )
        mToast!!.show()
    }

    fun distory() {
        mContext = null
    }
}