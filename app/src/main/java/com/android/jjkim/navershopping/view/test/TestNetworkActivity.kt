package com.android.jjkim.navershopping.view.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.common.Errors
import com.android.jjkim.navershopping.databinding.ActivityMainBinding
import com.android.jjkim.navershopping.service.ApiConstants
import com.android.jjkim.navershopping.service.ApiManager
import com.android.jjkim.navershopping.service.model.ResponseBase
import com.android.jjkim.navershopping.service.model.search.ResponseShopSearch
import com.android.jjkim.navershopping.service.model.search.ResponseShopSearchItem
import com.android.jjkim.navershopping.view.base.BaseActivity
import com.android.jjkim.navershopping.view.search.adapter.SearchResultAdapter

class TestNetworkActivity : BaseActivity() {
    private lateinit var searchEdit: EditText
    private lateinit var searchBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchResultAdapter: SearchResultAdapter

    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_test_network)

        initViews()
        requestNaverApi();
    }

    private fun initViews() {
        searchEdit = findViewById(R.id.edit_search)
        searchBtn = findViewById(R.id.btn_search)
        recyclerView = findViewById(R.id.recycler)
    }

    private fun displaySearchResult(itemList: ArrayList<ResponseShopSearchItem>) {
        searchResultAdapter = SearchResultAdapter(this)
        recyclerView.adapter = searchResultAdapter

        searchResultAdapter.itemList = itemList
        searchResultAdapter.notifyDataSetChanged()
    }

    private fun requestNaverApi() {
        ApiManager.getInstance()
            .requestSearchShop("모자", 1, 15, "sim",
                object : ApiManager.OnNetworkListener<ResponseShopSearch?> {
                    override fun OnNetworkResult(
                        requestId: ApiConstants.URL,
                        res: ResponseBase
                    ) {
                        if (res != null && (res as ResponseShopSearch).isSuccess) {
                            val itemList = res.items
                            if (itemList.size > 0) {
                                displaySearchResult(itemList)
                            }
                        } else {
                            Errors.show(this@TestNetworkActivity, res, false)
                        }
                    }
                })
    }
}