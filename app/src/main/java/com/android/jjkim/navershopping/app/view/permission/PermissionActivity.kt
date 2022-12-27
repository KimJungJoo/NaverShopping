/*
 * KT GoodPay version 1.0
 *
 * Copyright ⓒ 2019 kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in
 * compliance with license agreement with kt corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of kt corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.android.jjkim.navershopping.app.view.permission

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.app.view.base.BaseActivity
import com.android.jjkim.navershopping.common.datastore.NsDataStore
import com.android.jjkim.navershopping.databinding.ActivityPermissionBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PermissionActivity : BaseActivity(), View.OnClickListener {
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    lateinit var binding: ActivityPermissionBinding

    private var bChkAll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission)

        binding.cbPermissionStorage.setOnClickListener(this)
        binding.cbPermissionPhone.setOnClickListener(this)
        binding.cbPermissionAll.setOnClickListener(this)

        var isAgreeNecessaryPermission = true

        lifecycleScope.launch {
            NsDataStore().isAgreeNecessaryPermission.collect {
                isAgreeNecessaryPermission = it
            }
        }

        if(isAgreeNecessaryPermission) {
            //todo 어디 타는지 확인해보자
            binding.cbPermissionStorage.setChecked(true)
        } else {
            //todo 어디 타는지 확인해보자
            binding.cbPermissionStorage.setChecked(false)
        }

        binding.cbPermissionPhone.setChecked(mKccPreferences.isKccPhonenum())

        findViewById(R.id.layout_permission_storage).setOnClickListener(this)
        findViewById(R.id.layout_permission_phone).setOnClickListener(this)
        findViewById(R.id.layout_permission_all).setOnClickListener(this)

        val tv_permission_agree_desc: TextView = findViewById(R.id.tv_permission_agree_desc) as TextView

        CommonUtil.setTextColorChangePart(tv_permission_agree_desc, 0, 14, "#222222")

        if (!FingerprintUtils.isHardwareSupported(this)) {
            findViewById(R.id.layout_permission_finger).setVisibility(View.GONE)
        }

        btnConfirm = findViewById(R.id.btn_confirm) as Button?
        btnConfirm!!.setOnClickListener {
            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
                val mKccPreferences: KccPreferences = KccPreferences.getInstance(this@PermissionActivity)

                val now = date

                mKccPreferences.setKccNecessaryAgreeDate(now)
                mKccPreferences.setKccNecessary(true)
                mKccPreferences.setKccPhonenum(cbPhone.isChecked())

                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    protected fun checkEntry() {
//        super.checkEntry();
        if (!LocalPayApp.getApp().isInitialized) {
            autoExitErrorPopup("비정상적인 접근으로 인하여 앱을 종료합니다.")
        }
    }

    protected fun onResume() {
        super.onResume()
    }

    fun onBackPressed() {
//        setResult(Activity.RESULT_CANCELED);
//        finish();
        LocalPayApp.getApp().exitApp()
    }

    private fun checkAndRequestPermissions(): Boolean {
//        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.\permission.CAMERA);
        val permissionReadExternalStorage: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionWriteExternalStorage: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionReadPhone: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val permissionPhoneNumber: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS)
        val locationPermission: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionReadContacts: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        val listPermissionsNeeded: MutableList<String> = ArrayList()

        /*if (permissionCamera != PackageManager.PERMISSION_GRANTED) {   //카메라(필수)
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }*/if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {   //저장공간(필수)
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {   //저장공간(필수)
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if (permissionPhoneNumber != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_NUMBERS)
            }
        } else {
            if (permissionReadPhone != PackageManager.PERMISSION_GRANTED) {   //전화(필수)
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE)
            }
        }
        if (mCbLocation.isChecked()) {
            if (locationPermission != PackageManager.PERMISSION_GRANTED) {   //위치 (선택)
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private val date: String
        private get() {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        }

    /**
     * 사용자가 권한을 허용했는지 거부했는지 체크
     * @param requestCode   1번
     * @param permissions   개발자가 요청한 권한들
     * @param grantResults  권한에 대한 응답들
     * permissions와 grantResults는 인덱스 별로 매칭된다.
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (canAccessPerMissions()) {
                val mKccPreferences: KccPreferences = KccPreferences.getInstance(mActivity)
                val now = date
                mKccPreferences.setKccNecessaryAgreeDate(now)
                mKccPreferences.setKccNecessary(true)
                mKccPreferences.setKccLocation(mCbLocation.isChecked())
                mKccPreferences.setKccFinger(mCbFinger.isChecked())
                mKccPreferences.setKccPhonenum(mCbPhone.isChecked())
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(
                    getApplicationContext(),
                    "필수사항은 허용을 하셔야 서비스 이용이 가능합니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun canAccessPerMissions(): Boolean {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            (hasPermission(Manifest.permission.READ_PHONE_NUMBERS) //                    && hasPermission(Manifest.permission.CAMERA)
                    && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            (hasPermission(Manifest.permission.READ_PHONE_STATE) //                    && hasPermission(Manifest.permission.CAMERA)
                    && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
        } else {
            (hasPermission(Manifest.permission.READ_PHONE_STATE) //                    && hasPermission(Manifest.permission.CAMERA)
                    && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    fun hasPermission(perm: String?): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            mActivity,
            perm
        )
    }

    private fun checkComfirmEnable() {
//        if(mCbCarmera.isChecked() && mCbStorage.isChecked() && mCbPhone.isChecked())
        if (mCbStorage.isChecked() && mCbPhone.isChecked()) {
            btnConfirm!!.isEnabled = true
        } else {
            btnConfirm!!.isEnabled = false
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.layout_permission_storage -> mCbStorage.toggle()
            R.id.layout_permission_phone -> mCbPhone.toggle()
            R.id.layout_permission_location -> mCbLocation.toggle()
            R.id.layout_permission_finger -> mCbFinger.toggle()
            R.id.cb_permission_all, R.id.layout_permission_all -> mChkAll = if (!mChkAll) {
//                    mCbCarmera.setChecked(true);
                mCbStorage.setChecked(true)
                mCbPhone.setChecked(true)
                mCbLocation.setChecked(true)
                mCbFinger.setChecked(true)
                mCbAll.setChecked(true)
                true
            } else {
//                    mCbCarmera.setChecked(false);
                mCbStorage.setChecked(false)
                mCbPhone.setChecked(false)
                mCbLocation.setChecked(false)
                mCbFinger.setChecked(false)
                mCbAll.setChecked(false)
                false
            }
        }
        if (mCbStorage.isChecked() && mCbPhone.isChecked()
            && mCbLocation.isChecked() && mCbFinger.isChecked()
        ) {
            mCbAll.setChecked(true)
        } else {
            mCbAll.setChecked(false)
        }
        checkComfirmEnable()
    }
}