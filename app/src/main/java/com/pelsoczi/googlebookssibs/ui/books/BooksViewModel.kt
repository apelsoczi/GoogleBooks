package com.pelsoczi.googlebookssibs.ui.books

import androidx.lifecycle.ViewModel
import com.pelsoczi.googlebookssibs.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {



}