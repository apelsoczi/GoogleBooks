package com.pelsoczi.googlebookssibs.data.remote

import retrofit2.Response
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val apiService: BooksApiService
) {

    suspend fun fetch(
        startIndex: Int,
    ): Response<BooksApiResponse> {
       return apiService.search(
           startIndex = startIndex
       )
    }

}