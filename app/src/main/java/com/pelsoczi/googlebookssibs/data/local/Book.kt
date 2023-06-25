package com.pelsoczi.googlebookssibs.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem

@Entity(tableName = "books")
data class Book(
    @PrimaryKey @ColumnInfo val identifier: String,
    @ColumnInfo val title: String,
    @ColumnInfo val authors: String,
    @ColumnInfo val description: String,
    @ColumnInfo val published: String,
    @ColumnInfo val publisher: String,
    @ColumnInfo val pageCount: Int,
    @ColumnInfo val rating: Double,
    @ColumnInfo val reviews: Int,
    @ColumnInfo val thumbnail: String,
    @ColumnInfo val buyLink: String,
    @ColumnInfo val amount: Double,
    @ColumnInfo val currency: String,
) {
    fun forSale(): Boolean =
        buyLink.isNotEmpty() && amount > 0.0 && currency.isNotBlank()
}

fun BookItem.bookFromDTO() = Book(
    identifier = id,
    title = volumeInfo.title,
    authors = volumeInfo.authors.joinToString(", "),
    description = volumeInfo.description,
    published = volumeInfo.publishedDate,
    publisher = volumeInfo.publisher,
    pageCount = volumeInfo.pageCount,
    rating = volumeInfo.averageRating,
    reviews = volumeInfo.ratingsCount,
    thumbnail = when {
        volumeInfo.imageLinks.thumbnail.isNotBlank() -> volumeInfo.imageLinks.thumbnail
        volumeInfo.imageLinks.smallThumbnail.isNotBlank() -> volumeInfo.imageLinks.smallThumbnail
        else -> ""
    }.replace("http:", "https:"),
    buyLink = saleInfo.buyLink,
    amount = saleInfo.listPrice.amount,
    currency = saleInfo.listPrice.currencyCode,
)
