package com.android.jjkim.navershopping.common.utils

/**
 * 특정 조건이 만족될때까지 작업을 미루어 처리할 수 있는 매니저
 */

class DelayedWorkerManager {
    private var mWorked = false
    private val mDelayedWorkerList: MutableList<Runnable> = ArrayList()

    fun run() {
        mWorked = true
        for (worker in mDelayedWorkerList) {
            worker.run()
        }
        mDelayedWorkerList.clear()
    }

    fun work(worker: Runnable) {
        if (mWorked) {
            worker.run()
        } else {
            mDelayedWorkerList.add(worker)
        }
    }

    fun getSize(): Int {
        return mDelayedWorkerList.size
    }

    fun isWorked(): Boolean {
        return mWorked
    }
}