package com.android.jjkim.navershopping.app.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.jjkim.navershopping.databinding.DialogOneButtonBinding

class AlertDialog: DialogFragment() {
    private var _binding: DialogOneButtonBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: OnClickListener
    private lateinit var title: String
    private lateinit var msg: String
    private lateinit var btnTxt: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogOneButtonBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        binding.tvMsg.setText(msg)

        if(!TextUtils.isEmpty(title)) {
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvTitle.setText(title)
        }

        if(!TextUtils.isEmpty(btnTxt))
            binding.btnConfirm.setText(btnTxt)

        // 각 버튼 클릭 시 각각의 함수 호출
        binding.btnConfirm.setOnClickListener {
            listener.onConfirmClicked()
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setBtnText(txt: String) {
        this.btnTxt = txt;
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    interface OnClickListener {
        fun onConfirmClicked()
    }
}