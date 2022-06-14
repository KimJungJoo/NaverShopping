package com.android.jjkim.navershopping.view.tamplate

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.view.base.BaseActivity

class CollapsHeaderActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mLeftButton: ImageView
    private lateinit var mRightButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collaps)
    }

    fun init(title: String) {
        setTitle(title)
        initButton()
    }

    fun init(title: String, lVisible: Boolean, rVisible: Boolean) {
        setTitle(title)
        initButton()
        setLeftButtonVisible(lVisible)
        setRightButtonVisible(rVisible)
    }

    fun init(title: String, lVisible: Boolean, rVisible: Boolean, lEnable: Boolean, rEnable: Boolean) {
        setTitle(title)
        initButton()
        setLeftButtonVisible(lVisible)
        setRightButtonVisible(rVisible)
        setLeftButtonEnable(lEnable)
        setRightButtonEnable(rEnable)
    }

    fun setTitle(title: String) {
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(false);

        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ActionBar.LayoutParams ablp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);*/
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