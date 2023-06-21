package com.pelsoczi.googlebookssibs.data.remote

import com.google.gson.annotations.SerializedName


data class SearchInfo(

    @SerializedName("textSnippet") var textSnippet: String? = null

)