package com.pelsoczi.googlebookssibs.data

import androidx.paging.PagingSource.LoadResult
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.data.local.BooksDatabase
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import com.pelsoczi.googlebookssibs.util.isValid
import com.pelsoczi.googlebookssibs.util.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    database: BooksDatabase
) {

    private val daoBooks = database.booksDao()

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
    fun cachedBook(bookIdentifier: String): Book {
        return requireNotNull(
            pageCache.flatMap { it.data }.find { it.identifier == bookIdentifier }
        )
    }

    /** favorite a book */
    fun addFavorite(book: Book) {
        daoBooks.addFavorite(book)
    }

    /** remove a book from favorites */
    fun removeFavorite(book: Book) {
        daoBooks.removeFavorite(book)
    }

    /**
     * @return `true` when the book is in the database, `false` otherwise
     */
    fun isBookFavorited(book: Book): Flow<Boolean> {
        return daoBooks.getBook(book.identifier)
            .map { it != null }
    }

}