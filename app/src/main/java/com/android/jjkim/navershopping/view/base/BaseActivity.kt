package com.android.jjkim.navershopping.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.jjkim.navershopping.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}