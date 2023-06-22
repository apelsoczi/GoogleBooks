package com.pelsoczi.googlebookssibs.data

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.remote.BookItem
import com.pelsoczi.googlebookssibs.data.remote.BooksApiResponse
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import com.pelsoczi.googlebookssibs.util.isValid
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class RepositoryTest {

    private val networkDataSource = mockk<NetworkDataSource>()

    lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = Repository(
            networkDataSource = networkDataSource,
        )
    }

    @Test
    fun `api success and items returned by endpoint`() = runTest {
        // given
        val books = arrayListOf(BookItem())
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
        assertThat(result is PagingSource.LoadResult.Page).isTrue()
        result as PagingSource.LoadResult.Page
        assertThat(result.data).isEqualTo(books)
    }

    @Test
    fun `api success and no items returned by endpoint`() = runTest {
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
        val result = repository.loadBooks(0)
        // then
        assertThat(result is PagingSource.LoadResult.Invalid).isTrue()
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
        assertThat(result is PagingSource.LoadResult.Error).isTrue()
    }

}