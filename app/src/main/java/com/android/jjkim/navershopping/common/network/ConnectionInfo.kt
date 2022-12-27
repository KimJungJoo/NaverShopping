package com.android.jjkim.navershopping.common.network

import android.content.Context
import android.net.ConnectivityManager

class ConnectionInfo {

    companion object {
        var isNetworkConnected: Boolean = false

        fun hasNetwork(context: Context): Boolean {
            if (!isNetworkConnected) {
                val mgr =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = mgr.activeNetworkInfo
                return info != null && info.isConnected
            }
            return true
        }

        fun getWhatKindOfNetwork(context: Context): String? {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    return "WIFI"
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    return "MOBILE"
                }
            }
            return "NONE"
        }
    }
}