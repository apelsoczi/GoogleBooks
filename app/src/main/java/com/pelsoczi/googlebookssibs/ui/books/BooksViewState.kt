package com.pelsoczi.googlebookssibs.ui.books

import com.pelsoczi.googlebookssibs.data.local.Book

data class BooksViewState(
    val books: List<Book> = emptyList(),
    val invalid: Boolean = false,
    val error: Boolean = false,
    val nextKey: Int? = 0
)
