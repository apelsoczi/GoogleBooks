package com.pelsoczi.googlebookssibs.data.local

import com.pelsoczi.googlebookssibs.data.remote.model.BookItem

data class Book(
    val identifier: String,
    val title: String,
    val authors: String,
    val description: String,
    val published: String,
    val publisher: String,
    val thumbnail: String,
    val buyLink: String,
    val amount: Double,
    val currency: String,
) {
    val forSale: Boolean
        get() = buyLink.isNotEmpty()
}

fun BookItem.bookFromDTO() = Book(
    identifier = id,
    title = volumeInfo.title,
    authors = volumeInfo.authors.joinToString(", "),
    description = volumeInfo.description,
    published = volumeInfo.publishedDate,
    publisher = volumeInfo.publisher,
    thumbnail = when {
        volumeInfo.imageLinks.thumbnail.isNotBlank() -> volumeInfo.imageLinks.thumbnail
        volumeInfo.imageLinks.smallThumbnail.isNotBlank() -> volumeInfo.imageLinks.smallThumbnail
        else -> ""
    }.replace("http:", "https:"),
    buyLink = saleInfo.buyLink,
    amount = saleInfo.listPrice.amount,
    currency = saleInfo.listPrice.currencyCode,
)
