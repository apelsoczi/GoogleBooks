package com.pelsoczi.googlebookssibs.ui.books

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.pelsoczi.googlebookssibs.data.local.Book
import com.pelsoczi.googlebookssibs.ui.theme.GoogleBooksSIBSNewsTheme
import com.pelsoczi.googlebookssibs.ui.theme.Typography

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    val books = viewModel.viewState.collectAsLazyPagingItems()
    BooksScreen(
        books = books.itemSnapshotList,

    )
}

@Composable
private fun BooksScreen(
    books: ItemSnapshotList<Book>
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(books.filterNotNull()) { index, book ->
            book.title.let {
                Text(text = it, style = Typography.displayMedium)
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun BooksScreenPreview() {
    GoogleBooksSIBSNewsTheme {
        Surface {
            
        }
    }
}