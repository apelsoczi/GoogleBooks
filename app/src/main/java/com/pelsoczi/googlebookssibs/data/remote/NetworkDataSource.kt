package com.pelsoczi.googlebookssibs.data.remote

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val apiService: BooksApiService
) {

    fun fetch(): Single<Response<BooksApiResponse>> {
        return apiService.search()
            .observeOn(Schedulers.io())
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