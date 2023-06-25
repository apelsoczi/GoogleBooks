package com.pelsoczi.googlebookssibs.ui.favorites

import androidx.lifecycle.ViewModel
import com.pelsoczi.googlebookssibs.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

}