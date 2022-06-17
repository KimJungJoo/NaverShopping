package com.android.jjkim.navershopping.app

import android.app.Application

class NSApp : Application() {
    init {
        instance = this
    }

    companion object {
        var instance: NSApp? = null

        fun getApp() : NSApp {
            return instance as NSApp
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun exitApp() {
        System.runFinalization()
        System.exit(0)
    }
}