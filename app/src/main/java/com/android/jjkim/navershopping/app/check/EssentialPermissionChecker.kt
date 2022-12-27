package com.android.jjkim.navershopping.app.check

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.android.jjkim.navershopping.app.NSApp
import com.android.jjkim.navershopping.common.utils.AndroidInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class EssentialPermissionChecker: Checker() {
    private var mPermissions: ArrayList<Array<String>> = ArrayList<Array<String>>()

    init {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
            mPermissions.add(PermissionValues.PhoneNumber)
        else
            mPermissions.add(PermissionValues.Phones)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            mPermissions.add(PermissionValues.ReadStorage)
        else
            mPermissions.add(PermissionValues.Storages)
    }

    fun startCheck(listener: CheckListener) {
        //todo 기존 AsyncTask 를 Coroutine 으로 개선 진행 중..................
        setCheckListener(listener)

        CoroutineScope(Dispatchers.Default).async {
            checkPermission()
        }
    }

    suspend fun checkPermission() {
        for (permissions in mPermissions) {
            if (!hasPermissions(permissions)) {
                listener.onFail()
                return
            }
        }

        listener.onSuccess()
    }

    fun hasPermissions(permissions: Array<String>): Boolean {
        if (!AndroidInfo.hasMarshmallow()) {
            return true
        }

        for (permission in permissions) {
            if (!hasPermission(NSApp.getApp(), permission)) return false
        }
        return true
    }

    @TargetApi(23)
    private fun hasPermission(context: Context, permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}