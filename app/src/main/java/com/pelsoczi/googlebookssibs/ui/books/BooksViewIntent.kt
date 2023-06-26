package com.pelsoczi.googlebookssibs.ui.books

import com.pelsoczi.googlebookssibs.data.local.Book

sealed class BooksViewIntent {

    /** Load the next set of items */
    object LoadNext : BooksViewIntent()

    /** Select a book to view its details */
    data class ClickBook(
        val book: Book,
    ) : BooksViewIntent()

}
