package com.android.jjkim.navershopping.view.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.view.base.BaseActivity
import com.android.jjkim.navershopping.view.search.SearchActivity

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initButton()
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