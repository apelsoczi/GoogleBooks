package com.pelsoczi.googlebookssibs.data.remote

import com.google.gson.annotations.SerializedName


data class Epub(

    @SerializedName("isAvailable") var isAvailable: Boolean? = null

)