package com.pelsoczi.googlebookssibs.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.local.BooksDao
import com.pelsoczi.googlebookssibs.data.local.BooksDatabase
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var database: BooksDatabase
    private lateinit var booksDao: BooksDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, BooksDatabase::class.java)
            .build()
        booksDao = database.booksDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFavoriteBook() {
        // given
        val book = BookItem(id = "6DiXzQEACAAJ").bookFromDTO()
        //when
        runBlocking {
            booksDao.addFavorite(book)
        }
        //then
        runBlocking {
            booksDao.getAll().let {
                assertThat(it.first().id).isEqualTo(book.id)
            }
            booksDao.getBook(book.identifier).first().let {
                assertThat(it).isNotNull()
                assertThat(it?.identifier).isEqualTo(book.identifier)
            }
        }
    }

    @Test
    fun deleteFavoriteBook() {
        // given
        val book = BookItem(id = "6DiXzQEACAAJ").bookFromDTO()
        runBlocking { booksDao.addFavorite(book) }
        // when
        runBlocking { booksDao.removeFavorite(book) }
        // then
        runBlocking {
            booksDao.getAll().let {
                assertThat(it).isEmpty()
            }
            booksDao.getBook(book.identifier).first().let {
                assertThat(it).isNull()
            }
        }
    }

}