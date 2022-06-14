package com.android.jjkim.navershopping.viewmodel.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.jjkim.navershopping.service.model.search.ResponseShopSearch
import com.android.jjkim.navershopping.service.model.search.ResponseShopSearchItem
import com.android.jjkim.navershopping.service.repository.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel constructor(private val searchRepository: SearchRepository): ViewModel() {
    val resSearch = MutableLiveData<ResponseShopSearch>()
    val errorMessage = MutableLiveData<String>()

    fun getSearchShop(query: String?, start: Int, display: Int, sort: String?) {
        val response = searchRepository.requestSearchShop(query, start, display, sort)

        response!!.enqueue(object : Callback<ResponseShopSearch?> {
            override fun onResponse(call: Call<ResponseShopSearch?>, response: Response<ResponseShopSearch?>) {
                resSearch.postValue(response.body())
            }
            override fun onFailure(call: Call<ResponseShopSearch?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}