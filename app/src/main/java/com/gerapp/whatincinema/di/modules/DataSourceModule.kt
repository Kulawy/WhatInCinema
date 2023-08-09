package com.gerapp.whatincinema.di.modules

import com.gerapp.whatincinema.data.datasource.FavouriteHashSetDataSource
import com.gerapp.whatincinema.data.datasource.FavouriteLocalDataSource
import com.gerapp.whatincinema.data.datasource.MovieRemoteDataSource
import com.gerapp.whatincinema.data.datasource.MovieRemoteRetrofitDataSource
import com.gerapp.whatincinema.data.datasource.SearchRemoteDataSource
import com.gerapp.whatincinema.data.datasource.SearchRemoteRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideMovieDataSource(movieRemoteDataSource: MovieRemoteRetrofitDataSource): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideFavouriteDataSource(favouriteLocalDataSource: FavouriteHashSetDataSource): FavouriteLocalDataSource

    @Binds
    @Singleton
    abstract fun provideSearchRemoteDataSource(searchRemoteDataSource: SearchRemoteRetrofitDataSource): SearchRemoteDataSource
}
