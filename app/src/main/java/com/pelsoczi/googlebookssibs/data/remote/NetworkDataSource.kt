package com.pelsoczi.googlebookssibs.data.remote

import retrofit2.Response
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val apiService: BooksApiService
) {

    suspend fun fetch(): Response<BooksApiResponse> {
        return apiService.search()
//    val items = mutableListOf<Items>()
//        repeat(20) { i ->
//            withContext(currentCoroutineContext()) {
//                async {
//                    val max = 40
//                    val start = (40 * i) + 1
//                    val call = apiService.search(max = max, start = start)
//                    call.body()?.let {
//                        if (it.items.isEmpty()) {
//                            println("$max, $start")
//                        } else {
//                            items.addAll(it.items)
//                        }
//                    }
//                }.await()
//            }
//        }
    }

}