package com.android.jjkim.navershopping.common.utils

/**
 * Runnable에 임의의 값을 전달할 수 있는 러너블
 */
abstract class ValueWorker<T>(protected val value: T) : Runnable
