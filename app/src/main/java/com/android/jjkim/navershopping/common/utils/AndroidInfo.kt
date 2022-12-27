package com.android.jjkim.navershopping.common.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import java.util.*

class AndroidInfo {
    companion object {
        /**
         * 어플리케이션 버전 문자열을 반환한다.
         * @param context Context 객체
         * @return 버전 문자열
         */
        fun getAppVersion(context: Context): String? {
            try {
                val i = context.packageManager.getPackageInfo(
                    context.applicationContext.packageName,
                    PackageManager.GET_ACTIVITIES
                )
                return i.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                LogUtil.logException(e.message)
            }
            return null
        }

        /**
         * Application 이름을 반환한다.
         * @param context Context 객체
         * @return 어플리케이션 이름 문자열
         */
        fun getAppName(context: Context): String {
            val stringId = context.applicationInfo.labelRes
            return context.getString(stringId)
        }

        /**
         * 단말의 안드로이드 OS가 ICS MR1 이상인지의 여부를 반환한다.
         * @return
         */
        fun hasIcecreamSandwichMR1(): Boolean {
            return Build.VERSION.SDK_INT >= 15
        }

        /**
         * 단말의 안드로이드 OS가 Jellybean MR2 이상인지의 여부를 반환한다.
         * @return
         */
        fun hasJellybeanMR2(): Boolean {
            return Build.VERSION.SDK_INT >= 18
        }

        fun hasKitkat(): Boolean {
            return Build.VERSION.SDK_INT >= 19
        }

        fun hasKitkatWatch(): Boolean {
            return Build.VERSION.SDK_INT >= 20
        }

        fun hasNugart(): Boolean {
            return Build.VERSION.SDK_INT >= 24
        }


        /**
         * 단말의 안드로이드 OS가 lollipop 이상인지의 여부를 반환한다.
         * @return
         */
        fun hasLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= 21
        }

        /**
         * 단말의 안드로이드 OS가 Mashmallow 이상인지의 여부를 반환한다.
         * @return
         */
        fun hasMarshmallow(): Boolean {
            return Build.VERSION.SDK_INT >= 23
        }
    }

}