package com.gerapp.whatincinema.di.modules

import com.gerapp.whatincinema.data.localstorage.FavouriteObjectStorage
import com.gerapp.whatincinema.data.localstorage.FavouriteStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {

    @Singleton
    @Provides
    fun provideFavouriteStorage(): FavouriteStorage =
        FavouriteObjectStorage
}
