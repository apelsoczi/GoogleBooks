package com.pelsoczi.googlebookssibs.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelsoczi.googlebookssibs.data.LoadResult.Error
import com.pelsoczi.googlebookssibs.data.LoadResult.Invalid
import com.pelsoczi.googlebookssibs.data.LoadResult.Page
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.ui.books.BooksViewIntent.ClickBook
import com.pelsoczi.googlebookssibs.ui.books.BooksViewIntent.LoadNext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(BooksViewState(nextKey = 0))
    val viewState = _viewState.asStateFlow()

    fun handle(intent: BooksViewIntent) {
        when (intent) {
            is LoadNext -> load()
            is ClickBook -> {}
        }
    }

    private fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val nextKey = viewState.value.nextKey

            if (nextKey != null) {
                when (val loadResult = repository.loadBooks(nextKey)) {
                    is Page -> {
                        viewState.value.copy(
                            books = buildList {
                                addAll(_viewState.value.books)
                                addAll(loadResult.data)
                            },
                            nextKey = loadResult.nextKey,
                        )
                    }
                    is Invalid -> {
                        viewState.value.copy(
                            invalid = true,
                            nextKey = null,
                        )
                    }
                    is Error -> {
                        viewState.value.copy(
                            error = true,
                            nextKey = null,
                        )
                    }
                }.let {
                    _viewState.emit(it)
                }
            }
        }
    }

}