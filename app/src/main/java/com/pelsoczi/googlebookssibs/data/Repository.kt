package com.pelsoczi.googlebookssibs.data

import androidx.paging.PagingSource.LoadResult
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import com.pelsoczi.googlebookssibs.util.isValid
import com.pelsoczi.googlebookssibs.util.items
import javax.inject.Inject

class Repository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    /** cache the pages loaded */
    private val pageCache = mutableListOf<LoadResult.Page<Int, Book>>()

    /**
     * Fetch data from the api endpoint
     * @return the books fetched from the api service
     */
    suspend fun loadBooks(
        index: Int,
    ): LoadResult<Int, Book> {
        return try {
            val response = networkDataSource.fetch(
                startIndex = index,
            )
            if (response.isValid()) {
                LoadResult.Page(
                    data = response.items.map { it.bookFromDTO() },
                    prevKey = null,
                    nextKey = if (response.items.isNotEmpty()) response.items.size + index else null
                ).also {
                    pageCache.add(it)
                }
            } else {
                LoadResult.Invalid()
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /** retrieve a [Book] by its api identifier from the page loads */
    fun getBook(bookIdentifier: String): Book? {
        return pageCache.flatMap { it.data }.find { it.identifier == bookIdentifier }
    }

}