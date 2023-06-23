package com.pelsoczi.googlebookssibs.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.DetailDestination.ARG_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {

    private val book: Book? by lazy {
        repository.getBook(
            bookIdentifier = savedStateHandle.get<String>(ARG_ID) ?: ""
        )
    }

    private val _viewState = MutableStateFlow(DetailViewState(book = book))
    val viewState: StateFlow<DetailViewState> = _viewState.asStateFlow()

}