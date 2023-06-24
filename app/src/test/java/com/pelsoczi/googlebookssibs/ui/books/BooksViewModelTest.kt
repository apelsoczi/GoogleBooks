package com.pelsoczi.googlebookssibs.ui.books

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BooksViewModelTest {

    private val repository = mockk<Repository>()

    lateinit var pagingSourceController: PagingSourceController
    lateinit var viewModel: BooksViewModel

    @Before
    fun setUp() = runTest {
        pagingSourceController = PagingSourceController(repository)
        viewModel = BooksViewModel(
            repository = repository,
        )
    }

    @Test
    fun `error load result when server response results in exception`() = runTest {
        // given
        val refresh = LoadParams.Refresh(0, 60, true)
        val error = LoadResult.Error<Int, Book>(mockk())
        coEvery { repository.loadBooks(any()) } returns error
        // when
        val loadResult = viewModel.load(refresh)
        // then
        assertThat(loadResult).isEqualTo(error)
        coVerify { repository.loadBooks(refresh.key!!) }
    }

    @Test
    fun `invalid load result when server response is not valid`() = runTest {
        // given
        val refresh = LoadParams.Refresh(0, 60, true)
        val invalid = LoadResult.Invalid<Int, Book>()
        coEvery { repository.loadBooks(any()) } returns invalid
        // when
        val loadResult = viewModel.load(refresh)
        // then
        assertThat(loadResult).isEqualTo(invalid)
        coVerify { repository.loadBooks(refresh.key!!) }
    }

    @Test
    fun `page load result and next key when server response is valid`() = runTest {
        // given
        val refresh = LoadParams.Refresh(0, 60, true)
        val page = LoadResult.Page<Int, Book>(
            data = listOf(BookItem().bookFromDTO()),
            prevKey = null,
            nextKey = 1
        )
        coEvery { repository.loadBooks(any()) } returns page
        // when
        val loadResult = viewModel.load(refresh)
        // then
        assertThat(loadResult).isEqualTo(page)
        coVerify { repository.loadBooks(refresh.key!!) }
    }

    @Test
    fun `invalid load result when server response valid without more items to load`() = runTest {
        // given
        val refresh = LoadParams.Refresh(0, 60, true)
        val page = LoadResult.Page<Int, Book>(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )
        coEvery { repository.loadBooks(any()) } returns page
        // when
        val loadResult = viewModel.load(refresh)
        // then
        assertThat(loadResult).isNotEqualTo(page)
        assertThat(loadResult is LoadResult.Error<Int, Book>)
        coVerify { repository.loadBooks(refresh.key!!) }
    }

}