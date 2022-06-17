package com.android.jjkim.navershopping.app.view.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.app.NSApp
import com.android.jjkim.navershopping.app.view.base.BaseActivity
import com.android.jjkim.navershopping.app.view.search.SearchActivity
import com.android.jjkim.navershopping.common.AppExitHandler

class TestActivity : BaseActivity() {

    lateinit var exitHandler: AppExitHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        exitHandler = AppExitHandler(this)
        initButton()
    }

    override fun onBackPressed() {
        if(exitHandler.isBackPressed())
            return;

        NSApp.getApp().exitApp()
    }

    override fun onDestroy() {
        super.onDestroy()

        exitHandler?.let {
            it.distory()
        }
    }

    private fun initButton() {
        val networkTestButton = findViewById<Button>(R.id.networkTestButton)
        val headerTestButton = findViewById<Button>(R.id.headerTestButton)

        networkTestButton.setOnClickListener {
            val intent = Intent(baseContext, SearchActivity::class.java)
            startActivity(intent)
        }

        headerTestButton.setOnClickListener {
            val intent = Intent(baseContext, TestHeaderActivity::class.java)
            startActivity(intent)
        }
    }
}