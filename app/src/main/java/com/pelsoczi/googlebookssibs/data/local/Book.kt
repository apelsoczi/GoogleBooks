package com.pelsoczi.googlebookssibs.data.local

import com.pelsoczi.googlebookssibs.data.remote.model.BookItem

data class Book(
    val title: String,
    val authors: List<String>,
    val description: String,
    val published: String,
    val thumbnail: String,
    val buyLink: String,
    val amount: Double,
    val currency: String,
)

fun BookItem.bookFromDTO() = Book(
    title = volumeInfo.title,
    authors = volumeInfo.authors,
    description = volumeInfo.description,
    published = volumeInfo.publishedDate,
    thumbnail = volumeInfo.imageLinks.thumbnail,
    buyLink = saleInfo.buyLink,
    amount = saleInfo.listPrice.amount,
    currency = saleInfo.listPrice.currencyCode,
)
