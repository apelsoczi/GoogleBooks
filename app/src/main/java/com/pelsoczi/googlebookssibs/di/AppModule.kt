package com.pelsoczi.googlebookssibs.di

import android.content.Context
import androidx.room.Room
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.BooksDatabase
import com.pelsoczi.googlebookssibs.data.local.BooksDatabase.Companion.DB_NAME
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        networkDataSource: NetworkDataSource,
        database: BooksDatabase,
    ): Repository {
        return Repository(networkDataSource, database)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): BooksDatabase {
        val builder = Room
            .databaseBuilder(context, BooksDatabase::class.java, DB_NAME)
        return builder.build()
    }

}