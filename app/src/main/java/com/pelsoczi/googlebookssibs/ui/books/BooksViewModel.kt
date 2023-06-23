package com.pelsoczi.googlebookssibs.ui.books

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val pager: Pager<Int, Book> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            PagingSourceController(repository)
        },
    )

    val viewState: Flow<PagingData<Book>> = pager.flow

    internal class PagingSourceController(
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

}