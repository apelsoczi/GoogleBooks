package com.pelsoczi.googlebookssibs.data.remote

import com.google.gson.annotations.SerializedName


data class Pdf(

    @SerializedName("isAvailable") var isAvailable: Boolean? = null

)