package com.pelsoczi.googlebookssibs.data.remote.model

import com.google.gson.annotations.SerializedName


data class BookItem(

//    private @SerializedName("kind") var kind: String = "",
    @SerializedName("id") var id: String = "",
//    private @SerializedName("etag") var etag: String = "",
//    private @SerializedName("selfLink") var selfLink: String = "",
    @SerializedName("volumeInfo") var volumeInfo: VolumeInfo = VolumeInfo(),
    @SerializedName("saleInfo") var saleInfo: SaleInfo = SaleInfo(),
//    private @SerializedName("accessInfo") var accessInfo: AccessInfo? = AccessInfo(),
//    private @SerializedName("searchInfo") var searchInfo: SearchInfo? = SearchInfo()

)