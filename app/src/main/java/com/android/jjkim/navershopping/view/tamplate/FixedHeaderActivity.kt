package com.android.jjkim.navershopping.view.tamplate

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.view.base.BaseActivity

open class FixedHeaderActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mLeftButton: ImageView
    private lateinit var mRightButton: ImageView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun init(title: String?) {
        initViews()
        setTitle(title)
    }

    fun init(title: String?, lVisible: Boolean, rVisible: Boolean) {
        initViews()
        setTitle(title)
        setLeftButtonVisible(lVisible)
        setRightButtonVisible(rVisible)
    }

    private fun initViews() {
        mLeftButton = findViewById(R.id.leftButton)
        mRightButton = findViewById(R.id.rightButton)
        mLeftButton.setOnClickListener(this)
        mRightButton.setOnClickListener(this)
    }

    fun setTitle(title: String?) {
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = title
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.leftButton -> onClickToolbarLeftButton()
            R.id.rightButton -> onClickToolbarRightButton()
        }
    }

    fun setLeftButtonVisible(isVisible: Boolean) {
        mLeftButton.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        mLeftButton.isEnabled = isVisible
    }

    fun setRightButtonVisible(isVisible: Boolean) {
        mRightButton.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        mRightButton.isEnabled = isVisible
    }

    protected fun onClickToolbarLeftButton() {}
    protected fun onClickToolbarRightButton() {}
}