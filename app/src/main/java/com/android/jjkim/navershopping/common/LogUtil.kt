package com.android.jjkim.navershopping.common

import android.util.Log
import com.android.jjkim.navershopping.BuildConfig
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class LogUtil {

    companion object {
        private const val TAG_EXCEPTION = "exception"
        private const val fileOut = false
        const val debug = true

        fun v(TAG: String, msg: String) {
            if (debug && BuildConfig.DEBUG) {
                Log.v(TAG, msg)
            }
            writeFile(TAG, msg)
        }

        fun d(TAG: String, msg: String) {
            if (debug && BuildConfig.DEBUG) {
                //로그 잘림 방지 코드 for GvList
                if (msg.length > 3000) {
                    Log.d(TAG, msg.substring(0, 3000))
                    d(TAG, msg.substring(3000))
                } else {
                    Log.d(TAG, msg)
                }

//            Log.d(TAG, msg);
            }
            writeFile(TAG, msg)
        }

        fun w(TAG: String, msg: String) {
            if (debug && BuildConfig.DEBUG) {
                Log.w(TAG, msg)
            }
            writeFile(TAG, msg)
        }

        fun e(TAG: String, msg: String) {
            if (debug && BuildConfig.DEBUG) {
                Log.e(TAG, msg)
            }
            writeFile(TAG, msg)
        }

        fun i(TAG: String, msg: String) {
            if (debug && BuildConfig.DEBUG) {
                Log.i(TAG, msg)
            }
            writeFile(TAG, msg)
        }

        private fun writeFile(TAG: String, msg: String) {
            if (fileOut) {
                appendLog("$TAG|$msg")
                //            LogWrapper.getInstance().v(TAG,msg);
            }
        }

        fun appendLog(text: String) {
            val logFile = File("sdcard/log.txt")
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile()
                } catch (e: IOException) {
                    // 적절한 예외처리를 해주면됩니다.
                    logException(e.message)
                }
            }
            lateinit var buf: BufferedWriter
            try {
                //퍼포먼스를 위해 BufferedWriter를 썼고 FileWriter의 true는 파일을 이어쓸 수 있게 하기 위해서 해줬습니다.
                buf = BufferedWriter(FileWriter(logFile, true))
                buf.append(text)
                buf.newLine()
                buf.close()
            } catch (e: IOException) {
                // 적절한 예외처리를 해주면됩니다.
                logException(e.message)
            } finally {
                if (buf != null) {
                    try {
                        buf.close()
                    } catch (e: IOException) {
                        logException(e.message)
                    }
                }
            }
        }

        @kotlin.jvm.JvmStatic
        fun logException(msg: String?) {
            msg?.let { e(TAG_EXCEPTION, it) }
        }
    }
}