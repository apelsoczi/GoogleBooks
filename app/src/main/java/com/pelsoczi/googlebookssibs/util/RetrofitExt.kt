package com.pelsoczi.googlebookssibs.util

import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import com.pelsoczi.googlebookssibs.data.remote.model.BooksApiResponse
import retrofit2.Response

/**
 * @return true if a response is successful and body is not null
 */
fun Response<BooksApiResponse>.isValid() = this.isSuccessful && this.body() != null

/** Represent a response as a valid response when [isValid] */
typealias ValidResponse<reified T> = Response<T>

/**
 * Kotlin property access syntax for reified type access to list items for a
 * valid response of type BooksApiResponse. It's not perfect but hides some null
 * checking otherwise.
 */
val ValidResponse<BooksApiResponse>.items: List<BookItem>
    get() = this.body()!!.items