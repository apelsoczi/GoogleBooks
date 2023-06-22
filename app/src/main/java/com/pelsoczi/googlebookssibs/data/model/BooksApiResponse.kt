package com.pelsoczi.googlebookssibs.data.remote

import com.google.gson.annotations.SerializedName


data class BooksApiResponse(

    @SerializedName("kind") var kind: String? = null,
    @SerializedName("totalItems") var totalItems: Int? = null,
    @SerializedName("items") var items: ArrayList<BookItem> = arrayListOf()

)