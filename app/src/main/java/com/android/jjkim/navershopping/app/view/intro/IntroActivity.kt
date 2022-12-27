package com.android.jjkim.navershopping.app.view.intro

import android.content.Intent
import android.os.Bundle
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.app.check.Checker
import com.android.jjkim.navershopping.app.check.EssentialPermissionChecker
import com.android.jjkim.navershopping.app.view.base.BaseActivity
import com.android.jjkim.navershopping.app.view.dialog.AlertDialog
import com.android.jjkim.navershopping.app.view.test.TestActivity

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        checkEssentialPermission()
    }

    fun checkEssentialPermission() {
        val essentialPermissionChecker = EssentialPermissionChecker()

        essentialPermissionChecker.startCheck(object : Checker.CheckListener{
            override fun onSuccess() {
                val intent = Intent(baseContext, TestActivity::class.java)
                startActivity(intent)
            }

            override fun onFail() {
                val dialog = AlertDialog()
                dialog.setClickListener(object: AlertDialog.OnClickListener{
                    override fun onConfirmClicked() {

                    }
                })
                dialog.setMsg("필수 권한 동의가 필요합니다.")
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        })
    }
}