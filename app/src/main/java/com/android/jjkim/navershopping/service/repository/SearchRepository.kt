package com.android.jjkim.navershopping.service.repository

import com.android.jjkim.navershopping.service.FsRetofitInterface
import com.android.jjkim.navershopping.service.model.search.RequestShopSearch
import com.android.jjkim.navershopping.service.RestfulService

class SearchRepository {
    private val retrofitInterface: FsRetofitInterface = RestfulService.getInstance()

    suspend fun getSearchShop(requset: RequestShopSearch) =
        retrofitInterface.getShopSearch(requset)

    fun requestSearchShop(query: String?, start: Int, display: Int, sort: String?) =
        retrofitInterface.requestShopSearch(query, start, display, sort)
}