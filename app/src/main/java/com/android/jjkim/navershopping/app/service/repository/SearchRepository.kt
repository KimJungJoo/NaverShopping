package com.android.jjkim.navershopping.app.service.repository

import com.android.jjkim.navershopping.app.service.FsRetofitInterface
import com.android.jjkim.navershopping.app.service.model.search.RequestShopSearch
import com.android.jjkim.navershopping.app.service.RestfulService

class SearchRepository {
    private val retrofitInterface: FsRetofitInterface = RestfulService.getInstance()

    suspend fun getSearchShop(requset: RequestShopSearch) =
        retrofitInterface.getShopSearch(requset)

    suspend fun requestSearchShop(query: String?, start: Int, display: Int, sort: String?) =
        retrofitInterface.requestShopSearch(query, start, display, sort)
}