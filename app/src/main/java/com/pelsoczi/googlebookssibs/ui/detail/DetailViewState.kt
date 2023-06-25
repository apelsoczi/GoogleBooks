package com.pelsoczi.googlebookssibs.ui.detail

import com.pelsoczi.googlebookssibs.data.local.Book

/*** represents the view in the book detailed screen */
data class DetailViewState(
    val book: Book,
    val isFavorite: Boolean,
)
