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
import com.android.jjkim.navershopping.databinding.DialogTwoButtonBinding

class ConfirmDialog: DialogFragment() {
    private var _binding: DialogTwoButtonBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: OnClickListener
    private lateinit var title: String
    private lateinit var msg: String
    private lateinit var confrimTxt: String
    private lateinit var cancelTxt: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogTwoButtonBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        binding.tvMsg.setText(msg)

        if(!TextUtils.isEmpty(title)) {
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvTitle.setText(title)
        }

        if(!TextUtils.isEmpty(confrimTxt))
            binding.btnConfirm.setText(confrimTxt)

        if(!TextUtils.isEmpty(cancelTxt))
            binding.btnCancel.setText(cancelTxt)

        binding.btnConfirm.setOnClickListener {
            listener.onConfirmClicked()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            listener.onCancelClicked()
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

    fun setBtnText(confirmTxt: String, cancelTxt: String) {
        this.confrimTxt = confirmTxt;
        this.cancelTxt = cancelTxt
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    interface OnClickListener {
        fun onConfirmClicked()
        fun onCancelClicked()
    }
}