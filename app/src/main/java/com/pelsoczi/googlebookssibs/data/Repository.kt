package com.pelsoczi.googlebookssibs.data

import androidx.paging.PagingSource.LoadResult
import com.pelsoczi.googlebookssibs.data.remote.BookItem
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import com.pelsoczi.googlebookssibs.util.isValid
import com.pelsoczi.googlebookssibs.util.items
import javax.inject.Inject

class Repository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    /**
     * Fetch data from the api endpoint
     * @return the books fetched from the api service
     */
    suspend fun loadBooks(
        index: Int,
    ): LoadResult<Int, BookItem> {
        return try {
            val response = networkDataSource.fetch(
                startIndex = index,
            )
            if (response.isValid()) {
                LoadResult.Page(
                    data = response.items,
                    prevKey = null,
                    nextKey = if (response.items.isNotEmpty()) response.items.size + index else null
                )
            } else {
                LoadResult.Invalid()
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}