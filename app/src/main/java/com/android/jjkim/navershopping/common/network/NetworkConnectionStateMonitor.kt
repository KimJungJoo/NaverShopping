package com.android.jjkim.navershopping.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.android.jjkim.navershopping.common.utils.LogUtil

class NetworkConnectionStateMonitor constructor(context: Context): ConnectivityManager.NetworkCallback() {
    private val TAG = "NET_STATE"
    private var mContext: Context
    private var networkRequest: NetworkRequest
    private var connectivityManager: ConnectivityManager

    init {
        mContext = context

        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun register() {
        LogUtil.v(TAG, mContext.javaClass.simpleName + " connectivityManager registerNetworkCallback")
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun unregister() {
        LogUtil.v(TAG, mContext.javaClass.simpleName + " connectivityManager unregisterNetworkCallback")
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        //네트워크 연결됨
        ConnectionInfo.isNetworkConnected = true

        LogUtil.v(TAG, mContext.javaClass.simpleName + "last network connected")
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        //네트워크 연결끊김
        ConnectionInfo.isNetworkConnected = false
        LogUtil.v(TAG, mContext.javaClass.simpleName + "network disConnected")
    }
}