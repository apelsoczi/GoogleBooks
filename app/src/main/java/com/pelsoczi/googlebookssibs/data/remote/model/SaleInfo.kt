package com.pelsoczi.googlebookssibs.data.remote.model


import com.google.gson.annotations.SerializedName


data class SaleInfo(

//    @SerializedName("country") var country: String = "",
//    @SerializedName("saleability") var saleability: String = "",
//    @SerializedName("isEbook") var isEbook: Boolean? = null,
    @SerializedName("listPrice") var listPrice: SaleInfoListPrice = SaleInfoListPrice(),
//    @SerializedName("retailPrice") var retailPrice: SaleInfoRetailPrice = SaleInfoRetailPrice(),
    @SerializedName("buyLink") var buyLink: String = "",
//    @SerializedName("offers") var offers: ArrayList<Offers> = arrayListOf()

)