package com.android.jjkim.navershopping.service

object ApiConstants {
    const val NAVER_CLIENT_ID = "B7AAzOoYrngg_kyYmSvO"
    const val NAMER_CLIENT_SECRET = "1dK1haZmR2"

    const val BASE_URL = "https://openapi.naver.com/v1/"

    // 검색
    const val URL_SHOP_SEARCH = "search/shop.json" // 검색결과 목록
    const val URL_SEARCH_KEYWORD = "search/{type}/keywords" // 자동완성 검색 키워드, 인기, 추천 검색어
    const val URL_APP_VERSION_INFO = "localpay/mobile/main/getVersionInfo"

    enum class URL {
        BASE {
            override fun toString(): String {
                return BASE_URL
            }
        },
        SHOP_SEARCH {
            override fun toString(): String {
                return URL_SHOP_SEARCH
            }
        },
        APP_VERSION_INFO {
            override fun toString(): String {
                return URL_APP_VERSION_INFO
            }
        }
    }
}