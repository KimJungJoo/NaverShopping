package com.android.jjkim.navershopping.app

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.android.jjkim.navershopping.common.network.NetworkConnectionStateMonitor
import com.android.jjkim.navershopping.common.utils.LogUtil

class NSApp : Application() {
    private lateinit var networkConnectionStateMonitor: NetworkConnectionStateMonitor

    init {
        instance = this
    }

    companion object {
        var instance: NSApp? = null
        val android.content.Context.nsDataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore")

        fun getApp() : NSApp {
            return instance as NSApp
        }

        fun getDataStore() = getApp().nsDataStore
    }

    override fun onCreate() {
        super.onCreate()

        registNetworkChecker()
    }

    fun registNetworkChecker() {
        try {
            if (networkConnectionStateMonitor != null) networkConnectionStateMonitor.unregister()
        } catch (e: IllegalArgumentException) {
            LogUtil.logException(e.message)
        } catch (e: RuntimeException) {
            LogUtil.logException(e.message)
        } finally {
            networkConnectionStateMonitor = NetworkConnectionStateMonitor(this)
            networkConnectionStateMonitor.register()
        }
    }

    fun exitApp() {
        System.runFinalization()
        System.exit(0)
    }
}