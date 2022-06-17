package com.android.jjkim.navershopping.app.viewmodel.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jjkim.navershopping.app.service.model.search.ResponseShopSearch
import com.android.jjkim.navershopping.app.service.repository.SearchRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel constructor(private val searchRepository: SearchRepository): ViewModel() {
    val resSearch = MutableLiveData<ResponseShopSearch>()
    val errorMessage = MutableLiveData<String>()

    fun getSearchShop(query: String?, start: Int, display: Int, sort: String?) {
        viewModelScope.launch {
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
}