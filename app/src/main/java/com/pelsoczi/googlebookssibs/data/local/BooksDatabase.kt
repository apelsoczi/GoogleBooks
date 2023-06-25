package com.pelsoczi.googlebookssibs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class],
    version = 1,
)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun booksDao() : BooksDao

    companion object {
        val DB_NAME = "books-database"
    }
}