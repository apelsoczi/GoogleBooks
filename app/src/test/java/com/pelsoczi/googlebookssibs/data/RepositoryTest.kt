package com.pelsoczi.googlebookssibs.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.local.BooksDao
import com.pelsoczi.googlebookssibs.data.local.BooksDatabase
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import com.pelsoczi.googlebookssibs.data.remote.model.BooksApiResponse
import com.pelsoczi.googlebookssibs.util.isValid
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class RepositoryTest {

    private val networkDataSource = mockk<NetworkDataSource>()
    private val dao = mockk<BooksDao>(relaxed = true)
    private val database = mockk<BooksDatabase>() {
        coEvery { booksDao() } returns dao
    }

    lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = Repository(
            networkDataSource = networkDataSource,
            database = database,
        )
    }

    @Test
    fun `api success and items returned by endpoint`() = runTest {
        // given
        val books = arrayListOf(BookItem(id = "6DiXzQEACAAJ"))
        val body = mockk<BooksApiResponse>() {
            coEvery { items } returns books
        }
        val response = mockk<Response<BooksApiResponse>>() {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns body
            coEvery { isValid() } returns true
        }
        coEvery { networkDataSource.fetch(any()) } returns response
        // when
        val result = repository.loadBooks(0)
        // then
        assertThat(result is LoadResult.Page).isTrue()
        result as LoadResult.Page
        assertThat(result.data.first().equals(books.first().bookFromDTO()))
        assertThat(repository.cachedBook(books.first().id))
    }

    @Test
    fun `nextKey is null when no items were returned on api success`() = runTest {
        // given
        val books = arrayListOf<BookItem>()
        val body = mockk<BooksApiResponse>() {
            coEvery { items } returns books
        }
        val response = mockk<Response<BooksApiResponse>>() {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns body
            coEvery { isValid() } returns true
        }
        coEvery { networkDataSource.fetch(any()) } returns response
        // when
        println(response)
        val result = repository.loadBooks(0)
        // then
        assertThat(result is LoadResult.Page).isTrue()
        result as LoadResult.Page
        assertThat(result.data).isEmpty()
        assertThat(result.nextKey).isNull()
    }

    @Test
    fun `invoking api results in an error`() = runTest {
        // given
        val response = mockk<Response<BooksApiResponse>>() {
            coEvery { isSuccessful } returns false
            coEvery { body() } returns null
            coEvery { isValid() } returns false
        }
        coEvery { networkDataSource.fetch(any()) } throws UnknownHostException("")
        // when
        val result = repository.loadBooks(0)
        // then
        assertThat(result is LoadResult.Error).isTrue()
    }

    @Test
    fun `insert favorite book`() = runTest {
        // given
        val book = BookItem(id = "6DiXzQEACAAJ").bookFromDTO()
        coEvery { dao.book(any()) } returns flowOf(book)
        // when
        repository.isBookFavorited(book).test {
            repository.addFavorite(book)
            // then
            awaitItem().let {
                assertThat(it).isTrue()
            }
            awaitComplete()
        }
        coVerify { dao.addFavorite(book) }
        coVerify { dao.book(book.identifier) }
    }

    @Test
    fun `delete favorite book`() = runTest {
        // given
        val book = BookItem(id = "6DiXzQEACAAJ").bookFromDTO()
        coEvery { dao.book(any()) } returns flowOf(null)
        // when
        repository.isBookFavorited(book).test {
            repository.removeFavorite(book)
            // then
            awaitItem().let {
                assertThat(it).isFalse()
            }
            awaitComplete()
        }
        coVerify { dao.removeFavorite(book) }
        coVerify { dao.book(book.identifier) }
    }

}