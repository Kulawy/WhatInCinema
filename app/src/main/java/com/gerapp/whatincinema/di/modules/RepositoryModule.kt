package com.gerapp.whatincinema.di.modules

import com.gerapp.whatincinema.data.repository.FavouriteRepositoryImpl
import com.gerapp.whatincinema.data.repository.MovieRepositoryImpl
import com.gerapp.whatincinema.data.repository.SearchRepositoryImpl
import com.gerapp.whatincinema.domain.repository.FavouriteRepository
import com.gerapp.whatincinema.domain.repository.MovieRepository
import com.gerapp.whatincinema.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun provideFavouriteRepository(favouriteRepository: FavouriteRepositoryImpl): FavouriteRepository

    @Binds
    abstract fun provideSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}
