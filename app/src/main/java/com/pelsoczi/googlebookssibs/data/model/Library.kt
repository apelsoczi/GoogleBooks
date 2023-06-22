package com.pelsoczi.googlebookssibs.data.model

import com.pelsoczi.googlebookssibs.data.remote.BookItem

/**
 * Represents responses from the repository
 */
sealed class Library {

    /** Content found for the api call */
    data class Books(
        val books: List<BookItem>
    ) : Library()

    /** No content found for the api call */
    object EmptyShelf : Library()

    /** There was an exception fetching from the api */
    object NoContent : Library()

}