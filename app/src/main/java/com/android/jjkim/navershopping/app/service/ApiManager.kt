package com.android.jjkim.navershopping.app.service

import com.android.jjkim.navershopping.app.service.model.ResponseBase
import com.android.jjkim.navershopping.app.service.model.ResponseError
import com.android.jjkim.navershopping.app.service.model.search.ResponseShopSearch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ApiManager {
    var retrofitInterface: FsRetofitInterface

    init {
        retrofitInterface = RestfulService.getInstance()
    }

    companion object {
        private var mInstance: ApiManager? = null

        fun getInstance(): ApiManager {
            return mInstance ?: ApiManager().also {
                mInstance = it
            }
        }
    }

    interface OnNetworkListener<T> {
        fun OnNetworkResult(requestId: ApiConstants.URL, res: ResponseBase)
    }

    private fun setResult(listener: OnNetworkListener<*>?, requestId: ApiConstants.URL, res: ResponseBase) {
        listener?.OnNetworkResult(requestId, res)
    }

    private fun setResultError(listener: OnNetworkListener<*>?, requestId: ApiConstants.URL, res: ResponseBase) {
        setResultError(listener, requestId, "", res)
    }

    private fun setResultError(listener: OnNetworkListener<*>?, requestId: ApiConstants.URL, sub: String, res: ResponseBase) {
        listener?.OnNetworkResult(requestId, res)
    }

    fun parseError(response: Response<*>): ResponseError? {
        var error: ResponseError? = null

        try {
            //error = converter.convert(response.errorBody());
            val json = JSONObject(response.errorBody()?.string())
            //Logger.e("json  == " + json.toString());
            var retcode = ""
            var retmsg = ""

            if (json.has("retcode")) {
                retcode = json.getString("retcode")
            }

            if (json.has("retmsg")) {
                retmsg = json.getString("retmsg")
            }

            error = ResponseError(
                retcode,
                retmsg
            )
        } catch (e: IOException) {
            error = ResponseError(ResponseBase.RESPONSE_FAIL, e.toString())
        } catch (e: JSONException) {
            error = ResponseError(ResponseBase.RESPONSE_FAIL, e.toString())
        }
        return error
    }

    fun requestSearchShop(query: String?, start: Int, display: Int, sort: String?, listener: OnNetworkListener<*>?) {
        val service = retrofitInterface.requestShopSearch(query, start, display, sort)

        service!!.enqueue(object : Callback<ResponseShopSearch?> {
            override fun onResponse(call: Call<ResponseShopSearch?>, response: Response<ResponseShopSearch?>) {
                if (response.isSuccessful) {
                    response.body()?.let { setResult(listener, ApiConstants.URL.SHOP_SEARCH, it) }
                } else {
                    val error = parseError(response)
                    setResult(listener, ApiConstants.URL.SHOP_SEARCH, ResponseShopSearch(error?.resultCode, error?.resultMessage))
                }
            }

            override fun onFailure(call: Call<ResponseShopSearch?>, t: Throwable) {
                setResultError(listener, ApiConstants.URL.SHOP_SEARCH, ResponseShopSearch(t.toString()))
            }
        })
    }
}