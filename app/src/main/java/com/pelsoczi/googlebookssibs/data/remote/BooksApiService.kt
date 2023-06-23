package com.pelsoczi.googlebookssibs.data.remote


import com.pelsoczi.googlebookssibs.data.remote.model.BooksApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    @GET("volumes")
    suspend fun search(
        @Query("q") query: String = "android",
        @Query("startIndex") startIndex: Int = 0,
    ): Response<BooksApiResponse>

}