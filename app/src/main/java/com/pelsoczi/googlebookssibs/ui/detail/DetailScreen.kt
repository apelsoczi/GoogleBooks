package com.pelsoczi.googlebookssibs.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.theme.GoogleBooksSIBSNewsTheme
import com.pelsoczi.googlebookssibs.ui.theme.Typography

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    when (val book = viewState.value.book) {
        null -> navController.popBackStack()
        else -> DetailScreen(
            book = book,
        )
    }
}

@Composable
fun DetailScreen(
    book: Book
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
        Column {
            Text(
                text = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = Typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            ) {
                book.thumbnail.takeIf { it.isNotBlank() }?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .weight(2F)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(3F),
                ) {
                    book.authors.takeIf { it.isNotBlank() }?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = book.authors,
                            style = Typography.titleMedium,
                            color = Color.Blue,
                            maxLines = 3,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    book.publisher.takeIf { it.isNotBlank() }?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = book.publisher,
                            style = Typography.titleSmall,
                            color = Color.Gray,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                        )
                    }
                    book.published.takeIf { it.isNotBlank() }?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            style = Typography.titleSmall,
                            color = Color.Gray,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                        )
                    }

                    if ("${book.authors}${book.publisher}${book.published}".isNotBlank()) {
                        Divider(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp, vertical = 8.dp),
                            color = Color.LightGray,
                        )
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.align(Alignment.Center)) {
                            Spacer(modifier = Modifier.width(32.dp))
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1F)) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "${book.rating} ★",
                                    color = Color.Black,
                                    style = Typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = buildString {
                                        println()
                                        append(book.reviews.toString())
                                        append(" Review")
                                        if (book.reviews != 1) append("s")
                                    },
                                    color = Color.Gray,
                                    style = Typography.titleSmall,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            book.pageCount.takeIf { it > 0 }?.let {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1F)) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = it.toString(),
                                        color = Color.Black,
                                        style = Typography.titleMedium,
                                        textAlign = TextAlign.Center,
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Pages",
                                        color = Color.Gray,
                                        style = Typography.titleSmall,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }
        }

        Row {
            OutlinedButton(
                modifier = Modifier.padding(start = 16.dp),
                border = BorderStroke(1.dp, Color.Blue),
                onClick = {
                    println()
                },
            ) {
                Text(text = "Add to favorites", color = Color.Blue)
            }
            if (book.forSale) {
                Button(
                    modifier = Modifier.padding(start = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White,
                    ),
                    onClick = {
                        println()
                    },
                ) {
                    Text(text = "${book.amount} ${book.currency}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        book.description.takeIf { it.isNotEmpty() }?.let {
            Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
                Text(
                    text = "About this book",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    modifier = Modifier.fillMaxWidth(),
                    style = Typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    Surface{
        GoogleBooksSIBSNewsTheme {
            val book = Book(
                identifier = "cCHlCwAAQBAJ",
                title = "OpenCV Android Programming By Example",
                authors = "Amgad Muhammad",
                description = "Develop vision-aware and intelligent Android applications with the robust OpenCV library About This Book This is the most up-to-date book on OpenCV Android programming on the market at the moment. There is no direct competition for our title. Based on a technology that is increasing in popularity, proven by activity in forums related to this topic. This book uniquely covers applications such as the Panoramic viewer and Automatic Selfie, among others. Who This Book Is For If you are an Android developer and want to know how to implement vision-aware applications using OpenCV, then this book is definitely for you. It would be very helpful if you understand the basics of image processing and computer vision, but no prior experience is required What You Will Learn Identify and install all the elements needed to start building vision-aware Android applications Explore image representation, colored and gray scale Recognize and apply convolution operations and filtering to deal with noisy data Use different shape analysis techniques Extract and identify interest points in an image Understand and perform object detection Run native computer vision algorithms and gain performance boosts In Detail Starting from the basics of computer vision and OpenCV, we'll take you all the way to creating exciting applications. You will discover that, though computer vision is a challenging subject, the ideas and algorithms used are simple and intuitive, and you will appreciate the abstraction layer that OpenCV uses to do the heavy lifting for you. Packed with many examples, the book will help you understand the main data structures used within OpenCV, and how you can use them to gain performance boosts. Next we will discuss and use several image processing algorithms such as histogram equalization, filters, and color space conversion. You then will learn about image gradients and how they are used in many shape analysis techniques such as edge detection, Hough Line Transform, and Hough Circle Transform. In addition to using shape analysis to find things in images, you will learn how to describe objects in images in a more robust way using different feature detectors and descriptors. By the end of this book, you will be able to make intelligent decisions using the famous Adaboost learning algorithm. Style and approach An easy-to-follow tutorial packed with hands-on examples. Each topic is explained and placed in context, and the book supplies full details of the concepts used for added proficiency.",
                published = "2015-12-15",
                publisher = "Packt Publishing Ltd",
                pageCount = 202,
                rating = 4.5,
                reviews = 5,
                thumbnail = "http://books.google.com/books/content?id=cCHlCwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                buyLink = "https://play.google.com/store/books/details?id=cCHlCwAAQBAJ&rdid=book-cCHlCwAAQBAJ&rdot=1&source=gbs_api",
                amount = 21.19,
                currency = "EUR",
            )
            DetailScreen(book = book)
        }
    }
}