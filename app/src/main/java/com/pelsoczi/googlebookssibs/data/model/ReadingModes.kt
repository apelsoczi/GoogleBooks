package com.pelsoczi.googlebookssibs.data.remote

import com.google.gson.annotations.SerializedName


data class ReadingModes(

    @SerializedName("text") var text: Boolean? = null,
    @SerializedName("image") var image: Boolean? = null

)