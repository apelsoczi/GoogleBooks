package com.pelsoczi.googlebookssibs.ui.books

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.LoadResult
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import com.pelsoczi.googlebookssibs.ui.books.BooksViewIntent.LoadNext
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BooksViewModelTest {

    private val repository = mockk<Repository>()

    lateinit var viewModel: BooksViewModel

    @Before
    fun setUp() = runTest {
        viewModel = BooksViewModel(
            repository = repository,
        )
    }

    @Test
    fun `error load result when server response results in exception`() = runTest {
        // given
        val error = LoadResult.Error(mockk())
        coEvery { repository.loadBooks(any()) } returns error
        // when
        viewModel.viewState.test {
            viewModel.handle(LoadNext)
            // then
            skipItems(1)
            awaitItem().let {
                assertThat(it.nextKey).isNull()
                assertThat(it.books).isEmpty()
                assertThat(it.error).isTrue()
                assertThat(it.invalid).isFalse()
            }
        }
        coVerify { repository.loadBooks(0) }
    }

    @Test
    fun `invalid load result when server response is not valid`() = runTest {
        // given
        val invalid = LoadResult.Invalid
        coEvery { repository.loadBooks(any()) } returns invalid
        // when
        viewModel.viewState.test {
            viewModel.handle(LoadNext)
            // then
            skipItems(1)
            awaitItem().let {
                assertThat(it.nextKey).isNull()
                assertThat(it.books).isEmpty()
                assertThat(it.error).isFalse()
                assertThat(it.invalid).isTrue()
            }
        }
        coVerify { repository.loadBooks(0) }
    }

    @Test
    fun `server response with items to load and reaches end of items to load`() = runTest {
        // given
        val page = LoadResult.Page(
            data = listOf(BookItem().bookFromDTO()),
            nextKey = 1
        )
        coEvery { repository.loadBooks(any()) } returns page
        // when
        viewModel.viewState.test {
            viewModel.handle(LoadNext)
            // then
            skipItems(1)
            awaitItem().let {
                assertThat(it.nextKey).isEqualTo(page.nextKey)
                assertThat(it.books).isEqualTo(page.data)
                assertThat(it.error).isFalse()
                assertThat(it.invalid).isFalse()
            }
            // and given
            val page2 = page.copy(
                data = emptyList(),
                nextKey = null,
            )
            coEvery { repository.loadBooks(any()) } returns page2
            // and when
            viewModel.handle(LoadNext)
            // and then
            awaitItem().let {
                assertThat(it.nextKey).isNull()
                assertThat(it.books).isEqualTo(page.data)
                assertThat(it.error).isFalse()
                assertThat(it.invalid).isFalse()
            }
        }
        coVerify(atMost = 1) { repository.loadBooks(0) }
    }

}