package com.pelsoczi.googlebookssibs.ui.books

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.BookViewHolder

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel(),
    onClickBook: (book: Book) -> Unit,
) {
    val books = viewModel.viewState.collectAsLazyPagingItems()
    BooksScreen(
        books = books,
        onClickBook = { onClickBook(it) }
    )
}

@Composable
private fun BooksScreen(
    books: LazyPagingItems<Book>,
    onClickBook: (book: Book) -> Unit,
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(books.itemSnapshotList.filterNotNull()) { index, book ->
            BookViewHolder(
                book = book,
                onClickBook = {
                    onClickBook(it)
                },
            )
            Divider()
        }
    }
}