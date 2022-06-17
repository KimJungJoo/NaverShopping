package com.android.jjkim.navershopping.app.view.search

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.common.FragmentTransitionManager
import com.android.jjkim.navershopping.databinding.ActivityBaseBinding
import com.android.jjkim.navershopping.app.view.base.BaseActivity
import com.android.jjkim.navershopping.app.view.search.adapter.SearchResultAdapter

class SearchActivity : BaseActivity() {
    private lateinit var searchEdit: EditText
    private lateinit var searchBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchResultAdapter: SearchResultAdapter

    lateinit var viewBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if(savedInstanceState == null)
            setSearchFragment()
    }

    private fun setSearchFragment() {
        FragmentTransitionManager()
            .changeFragmentOnActivity(
                this,
//                viewBinding.mainContainer.id,
                R.id.main_container,
                SearchFragment(),
                false)
    }
}