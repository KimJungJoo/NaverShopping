package com.android.jjkim.navershopping.view.tamplate

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.view.base.BaseActivity


class ScrollingHeaderActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mLeftButton: ImageView
    private lateinit var mRightButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_header)
    }

    fun init(title: String?) {
        initButton()
        setTitle(title)
    }

    fun init(title: String?, lVisible: Boolean, rVisible: Boolean) {
        initButton()
        setTitle(title)
        setLeftButtonVisible(lVisible)
        setRightButtonVisible(rVisible)
    }

    fun init(title: String?, lVisible: Boolean, rVisible: Boolean, lEnable: Boolean, rEnable: Boolean) {
        initButton()
        setTitle(title)
        setLeftButtonVisible(lVisible)
        setRightButtonVisible(rVisible)
        setLeftButtonEnable(lEnable)
        setRightButtonEnable(rEnable)
    }

    fun setTitle(title: String?) {
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = title
    }

    private fun initButton() {
        mLeftButton = findViewById(R.id.rightButton)
        mRightButton = findViewById(R.id.leftButton)
        mLeftButton.setOnClickListener(this)
        mRightButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.leftButton -> {}
            R.id.rightButton -> {}
        }
    }

    fun setLeftButtonVisible(isVisible: Boolean) {
        mLeftButton.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        mLeftButton.isClickable = isVisible
    }

    fun setRightButtonVisible(isVisible: Boolean) {
        mRightButton.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        mRightButton.isClickable = isVisible
    }

    fun setLeftButtonEnable(isEnable: Boolean) {
        mLeftButton.isEnabled = isEnable
    }

    fun setRightButtonEnable(isEnable: Boolean) {
        mRightButton.isEnabled = isEnable
    }
}