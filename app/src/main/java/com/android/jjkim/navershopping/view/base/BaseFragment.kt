package com.android.jjkim.navershopping.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.android.jjkim.navershopping.R

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class BaseFragment<T: ViewDataBinding> : Fragment() {

    lateinit var viewDataBinding: T
    abstract var layoutId: Int
    abstract val viewModel: ViewModel

    private lateinit var mParam1: String
    private lateinit var mParam2: String

    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        init()

        return viewDataBinding.root
    }
}