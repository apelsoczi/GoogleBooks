package com.pelsoczi.googlebookssibs.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    suspend fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE :identifier LIKE identifier")
    fun getBook(identifier: String): Flow<Book?>

    @Insert
    fun addFavorite(book: Book)

    @Delete
    fun removeFavorite(book: Book)

    /** delete everything */
    @Query("DELETE FROM books")
    fun deleteAll()

}