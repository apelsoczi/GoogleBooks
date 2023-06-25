package com.pelsoczi.googlebookssibs.data.local

import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BookTest {

    @Test
    fun `book is for sale`() = runTest {
        // given
        val buylink = "https://play.google.com/store/books/details?id=cCHlCwAAQBAJ&rdid=book-cCHlCwAAQBAJ&rdot=1&source=gbs_api"
        val amount = 100
        val currency = "EUR"
        // when
        val book = BookItem().bookFromDTO()
        // then
        assertThat(book.forSale()).isFalse()
        // and when
        book.copy(buyLink = buylink).let {
            // and then
            assertThat(it.forSale()).isFalse()
        }
        // and when
        book.copy(buyLink = buylink, amount = 100.0).let {
            // and then
            assertThat(it.forSale()).isFalse()
        }
        // and when
        book.copy(buyLink = buylink, amount = 100.0, currency = "EUR").let {
            // and then
            assertThat(it.forSale()).isTrue()
        }
    }

}