package com.android.jjkim.navershopping.service.model.search

import com.google.gson.annotations.SerializedName

data class ResponseShopSearchItem (
    @SerializedName("title")
    var title: String? = null, //검색 결과 문서의 제목을 나타낸다. 제목에서 검색어와 일치하는 부분은 태그로 감싸져 있다.

    @SerializedName("link")
    var link: String? = null,  //검색 결과 문서의 하이퍼텍스트 link를 나타낸다.

    @SerializedName("image")
    var image: String? = null,  //썸네일 이미지의 URL이다. 이미지가 있는 경우만 나타난다.

    @SerializedName("lprice")
    var lprice: String? = null,  //최저가 정보이다. 최저가 정보가 없는 경우 0으로 표시되며, 가격비교 데이터가 없는 경우 이 필드는 가격을 나타낸다.

    @SerializedName("hprice")
    var hprice: String? = null,  //최고가 정보이다. 최고가 정보가 없거나 가격비교 데이터가 없는 경우 0으로 표시된다.

    @SerializedName("mallName")
    var mallName: String? = null,  //상품을 판매하는 쇼핑몰의 상호이다. 정보가 없을 경우 네이버로 표기된다.

    @SerializedName("productId")
    var productId: String? = null,  //해당 상품에 대한 ID 이다.

    @SerializedName("productType")
    var productType: String? = null,  //상품군 정보를 일반상품, 중고상품, 단종상품, 판매예정상품으로 구분한다.

    @SerializedName("maker")
    var maker: String? = null,   //해당 상품의 제조사 명이다.

    @SerializedName("brand")
    var brand: String? = null,   //해당 상품의 브랜드 명이다.

    @SerializedName("category1")
    var category1: String? = null,   //해당 상품의 카테고리, 대분류이다.

    @SerializedName("category2")
    var category2: String? = null,   //해당 상품의 카테고리, 중분류이다.

    @SerializedName("category3")
    var category3: String? = null,   //해당 상품의 카테고리, 소분류이다.

    @SerializedName("category4")
    var category4: String? = null   //해당 상품의 카테고리, 세분류이다.
)