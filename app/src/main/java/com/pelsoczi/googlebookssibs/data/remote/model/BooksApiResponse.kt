package com.pelsoczi.googlebookssibs.data.remote.model

import com.google.gson.annotations.SerializedName


data class BooksApiResponse(

//    @SerializedName("kind") var kind: String = "",
//    @SerializedName("totalItems") var totalItems: Int? = null,
    @SerializedName("items") var items: ArrayList<BookItem> = arrayListOf()

)