package com.android.jjkim.navershopping.app

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class NSApp : Application() {
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
    }

    fun exitApp() {
        System.runFinalization()
        System.exit(0)
    }
}