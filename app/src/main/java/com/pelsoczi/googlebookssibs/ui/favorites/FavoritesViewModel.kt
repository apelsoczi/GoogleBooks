package com.pelsoczi.googlebookssibs.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(emptyList<Book>())
    val viewState: StateFlow<List<Book>> = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.favorites().collect {
                _viewState.emit(it)
            }
        }
    }

}