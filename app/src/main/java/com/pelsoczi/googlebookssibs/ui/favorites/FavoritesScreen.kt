package com.pelsoczi.googlebookssibs.ui.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.BookViewHolder
import com.pelsoczi.googlebookssibs.ui.previewBook
import com.pelsoczi.googlebookssibs.ui.theme.GoogleBooksSIBSTheme

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onClickBook: (book: Book) -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    if (viewState.value.isNotEmpty()) {
        FavoritesScreen(
            books = viewState.value,
            onClickBook = {
                onClickBook(it)
            }
        )
    }
}

@Composable
fun FavoritesScreen(
    books: List<Book>,
    onClickBook: (Book) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(books.filterNotNull()) { index, book ->
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

@Preview
@Composable
fun FavoritesScreenPreview() {
    Surface {
        GoogleBooksSIBSTheme {
            FavoritesScreen(
                books = listOf(previewBook, previewBook, previewBook, previewBook, previewBook),
                onClickBook = {},
            )
        }
    }
}
