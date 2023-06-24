package com.pelsoczi.googlebookssibs.ui.books

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    // required to be lateinit, DI and instantiating PagingSource outside of factory throws
    // java.lang.IllegalStateException: An instance of PagingSource was re-used when Pager expected
    // to create a new instance. Ensure that the pagingSourceFactory passed to Pager always returns
    // a new instance of PagingSource.
    private lateinit var pagingSourceController: PagingSourceController

    private val pager: Pager<Int, Book> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
              PagingSourceController(repository).also { pagingSourceController = it }
        },
    )

    val viewState: Flow<PagingData<Book>> = pager.flow
        .cachedIn(viewModelScope)

    /**
     * Visible for "unit testing" flow and being able to confidently assert emissions,
     * and integration testing with Paging3.
     *
     * When this viewModel is initialized the flow is collected and [pagingSourceController]
     * is invoked immediately. Unfortunately even with turbine library the flow is not collected
     * as expected (PagingData<T>) is emitted, without any properties to assert and also unable
     * to assert type T. Unit testing with `runTest {}` and using `.asSnapshot()` according
     * to documentation is not stable.
     *
     * @return the LoadResult to assert emissions which are broadcasted to [viewState]
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Book> {
        return pagingSourceController.load(params)
    }


}

class PagingSourceController(
    private val repository: Repository,
) : PagingSource<Int, Book>() {

    /** Fetch the data from index 0 if pull to swipe is implemented */
    override fun getRefreshKey(state: PagingState<Int, Book>): Int = 0

    /**
     * Fetches the data from the repository and returns the load result, or invalidates
     * from loading further data if params.key is null - we have reached end of list
     * from the server.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val indexKey = params.key ?: return LoadResult.Invalid()
        val loadResult = repository.loadBooks(index = indexKey)
        return loadResult
    }
}