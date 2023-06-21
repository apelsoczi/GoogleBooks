package com.pelsoczi.googlebookssibs.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    @GET("volumes")
    fun search(
        @Query("q") query: String = "android",
        @Query("maxResult") max: Int = 40,
        @Query("startIndex") start: Int = 0,
    ): Single<Response<BooksApiResponse>>

}