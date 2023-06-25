package com.pelsoczi.googlebookssibs.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.detail.DetailViewIntent.AddToFavorites
import com.pelsoczi.googlebookssibs.ui.detail.DetailViewIntent.Purchase
import com.pelsoczi.googlebookssibs.ui.detail.DetailViewIntent.RemoveFavorite
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.DetailDestination.ARG_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {

    private val book: Book = repository.cachedBook(
        bookIdentifier = requireNotNull(savedStateHandle.get<String>(ARG_ID))
    )

    private val _viewState = MutableStateFlow<DetailViewState?>(null)
    val viewState: StateFlow<DetailViewState?> = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(flowOf(book), repository.isBookFavorited(book)) { book, isFavorite ->
                DetailViewState(book, isFavorite)
            }.onEach {
               _viewState.emit(it)
            }.collect()
        }
    }

    /** accessor function for submitting actions to the view model */
    fun handle(intent: DetailViewIntent) {
        when (intent) {
            AddToFavorites -> toggleFavorite()
            RemoveFavorite -> toggleFavorite()
            Purchase -> {}
        }
    }

    private fun toggleFavorite() = viewModelScope.launch(Dispatchers.IO) {
        val isFavorite = repository.isBookFavorited(book).first()
        if (isFavorite) repository.removeFavorite(book)
        else repository.addFavorite(book)
    }

}