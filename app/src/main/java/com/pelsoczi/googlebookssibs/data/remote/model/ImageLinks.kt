package com.pelsoczi.googlebookssibs.data.remote.model

import com.google.gson.annotations.SerializedName


data class ImageLinks(

    @SerializedName("smallThumbnail") var smallThumbnail: String = "",
    @SerializedName("thumbnail") var thumbnail: String = ""

)