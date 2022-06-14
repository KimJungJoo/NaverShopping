package com.android.jjkim.navershopping.service

import com.android.jjkim.navershopping.service.model.search.RequestShopSearch
import com.android.jjkim.navershopping.service.model.search.ResponseShopSearch
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface FsRetofitInterface {

    /**
     * 네이버 쇼핑 검색
     */
    @GET(ApiConstants.URL_SHOP_SEARCH)
    fun requestShopSearch(
        @Query("query") query: String?,
        @Query("start") start: Int,
        @Query("display") display: Int,
        @Query("sort") sort: String?
    ): Call<ResponseShopSearch?>?

    @GET(ApiConstants.URL_SHOP_SEARCH)
    suspend fun getShopSearch(
        @Body params: RequestShopSearch
    ): Response<ResponseShopSearch?>?
}