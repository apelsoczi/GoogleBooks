package com.pelsoczi.googlebookssibs.data.remote.model

import com.google.gson.annotations.SerializedName


data class SaleInfoListPrice(

    @SerializedName("amount") var amount: Double = 0.0,
    @SerializedName("currencyCode") var currencyCode: String = ""

)