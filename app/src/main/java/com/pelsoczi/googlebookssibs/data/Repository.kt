package com.pelsoczi.googlebookssibs.data

import com.pelsoczi.googlebookssibs.data.model.Library
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    /**
     * Fetch data from the api endpoint
     * @return the books fetched from the api service
     */
    // TODO: test coverage
    suspend fun getBooks(): Library {
        val response = networkDataSource.fetch()
        return if (response.isSuccessful) {
            val content = response.body()?.items?.let {
                Library.Books(it.toList())
            } ?: Library.EmptyShelf
            return content
        } else {
            Library.NoContent
        }
    }

}