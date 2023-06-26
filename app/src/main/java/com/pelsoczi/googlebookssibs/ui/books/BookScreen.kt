package com.pelsoczi.googlebookssibs.ui.books

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.BookViewHolder
import com.pelsoczi.googlebookssibs.ui.books.BooksViewIntent.ClickBook
import com.pelsoczi.googlebookssibs.ui.books.BooksViewIntent.LoadNext
import com.pelsoczi.googlebookssibs.util.RememberSaveableEffect

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel(),
    onClickBook: (book: Book) -> Unit,
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    RememberSaveableEffect {
        viewModel.handle(LoadNext)
    }
    if (viewState.value.books.isNotEmpty()) {
        BooksScreen(
            books = viewState.value.books,
            handle = {
                when (it) {
                    is LoadNext -> viewModel.handle(it)
                    is ClickBook -> onClickBook(it.book)
                }
            }
        )
    }
}

@Composable
private fun BooksScreen(
    books: List<Book>,
    handle: (BooksViewIntent) -> Unit,
) {
    val listState = rememberLazyListState()
    val nestedScrollConnection = object : NestedScrollConnection {
        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            val scrolledToEnd = listState.layoutInfo.visibleItemsInfo.last().index ==
                    listState.layoutInfo.totalItemsCount - 1
            if (scrolledToEnd) handle(LoadNext)
            return super.onPostFling(consumed, available)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection),
        state = listState
    ) {
        itemsIndexed(books) { index, book ->
            BookViewHolder(
                book = book,
                onClickBook = {
                    handle(ClickBook(book))
                },
            )
            Divider()
        }
    }
}