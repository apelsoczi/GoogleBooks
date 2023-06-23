package com.pelsoczi.googlebookssibs.ui.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.theme.GoogleBooksSIBSNewsTheme
import com.pelsoczi.googlebookssibs.ui.theme.Typography

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    val books = viewModel.viewState.collectAsLazyPagingItems()
    BooksScreen(
        books = books,
    )
}

@Composable
private fun BooksScreen(
    books: LazyPagingItems<Book>
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(books.itemSnapshotList.filterNotNull()) { index, book ->
            BookViewHolder(book = book)
            Divider()
        }
    }
}

@Composable
private fun BookViewHolder(
    book: Book
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(2F)
                .padding(8.dp),
        ) {
            Text(
                text = book.title,
                style = Typography.titleLarge,
                fontWeight = FontWeight.Light,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.authors,
                style = Typography.titleMedium,
                color = Color.Gray,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.publisher,
                style = Typography.titleSmall,
                color = Color.Gray,
                maxLines = 1,
            )
        }
        book.thumbnail.takeIf { it.isNotBlank() }?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .weight(1F)
            )
        }
    }
}

@Preview
@Composable
fun BooksScreenPreview() {
    Surface {
        GoogleBooksSIBSNewsTheme {
            val book = Book(
                identifier = "cCHlCwAAQBAJ",
                title = "OpenCV Android Programming By Example",
                authors = "Amgad Muhammad",
                description = "Develop vision-aware and intelligent Android applications with the robust OpenCV library About This Book This is the most up-to-date book on OpenCV Android programming on the market at the moment. There is no direct competition for our title. Based on a technology that is increasing in popularity, proven by activity in forums related to this topic. This book uniquely covers applications such as the Panoramic viewer and Automatic Selfie, among others. Who This Book Is For If you are an Android developer and want to know how to implement vision-aware applications using OpenCV, then this book is definitely for you. It would be very helpful if you understand the basics of image processing and computer vision, but no prior experience is required What You Will Learn Identify and install all the elements needed to start building vision-aware Android applications Explore image representation, colored and gray scale Recognize and apply convolution operations and filtering to deal with noisy data Use different shape analysis techniques Extract and identify interest points in an image Understand and perform object detection Run native computer vision algorithms and gain performance boosts In Detail Starting from the basics of computer vision and OpenCV, we'll take you all the way to creating exciting applications. You will discover that, though computer vision is a challenging subject, the ideas and algorithms used are simple and intuitive, and you will appreciate the abstraction layer that OpenCV uses to do the heavy lifting for you. Packed with many examples, the book will help you understand the main data structures used within OpenCV, and how you can use them to gain performance boosts. Next we will discuss and use several image processing algorithms such as histogram equalization, filters, and color space conversion. You then will learn about image gradients and how they are used in many shape analysis techniques such as edge detection, Hough Line Transform, and Hough Circle Transform. In addition to using shape analysis to find things in images, you will learn how to describe objects in images in a more robust way using different feature detectors and descriptors. By the end of this book, you will be able to make intelligent decisions using the famous Adaboost learning algorithm. Style and approach An easy-to-follow tutorial packed with hands-on examples. Each topic is explained and placed in context, and the book supplies full details of the concepts used for added proficiency.",
                published = "2015-12-15",
                publisher = "Packt Publishing Ltd",
                thumbnail = "http://books.google.com/books/content?id=cCHlCwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                buyLink = "https://play.google.com/store/books/details?id=cCHlCwAAQBAJ&rdid=book-cCHlCwAAQBAJ&rdot=1&source=gbs_api",
                amount = 21.19,
                currency = "EUR",
            )
            BookViewHolder(book = book)
        }
    }
}