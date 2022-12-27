package com.android.jjkim.navershopping.common.listener

/**
 * 백키 이벤트를 감지하여 처리할 수 있는 리스너
 */
interface OnBackPressedListener {
    fun onBackPressed(): Boolean
}