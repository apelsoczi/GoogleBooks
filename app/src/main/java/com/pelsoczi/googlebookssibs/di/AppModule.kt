package com.pelsoczi.googlebookssibs.di

import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.remote.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        networkDataSource: NetworkDataSource
    ): Repository {
        return Repository(networkDataSource)
    }

}