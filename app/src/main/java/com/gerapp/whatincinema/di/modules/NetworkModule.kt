package com.gerapp.whatincinema.di.modules

import android.content.Context
import com.gerapp.whatincinema.BuildConfig
import com.gerapp.whatincinema.data.DataConstants.NOW_PLAYING_API_URL
import com.gerapp.whatincinema.data.DataConstants.SEARCH_API_URL
import com.gerapp.whatincinema.data.mapper.NetworkResponseMapper
import com.gerapp.whatincinema.data.mapper.NetworkResponseRetrofitMapper
import com.gerapp.whatincinema.data.network.api.TheMovieDbApi
import com.gerapp.whatincinema.data.network.api.TheMovieDbSearchApi
import com.gerapp.whatincinema.data.network.interceptor.AuthorizationInterceptor
import com.gerapp.whatincinema.data.network.interceptor.CacheInterceptor
import com.gerapp.whatincinema.data.network.interceptor.OfflineCacheInterceptor
import com.gerapp.whatincinema.data.network.persistance.AuthPersistentStorage
import com.gerapp.whatincinema.data.network.persistance.AuthPersistentStorageLocalProperties
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val MOVIES_API = "movies.api"
    private const val NOW_PLAYING_API = "now_playing.api"
    private const val SEARCH_API = "search.api"

    private const val TIMEOUT_IN_SECONDS = 15
    private val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }
    private const val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB cache size

    @Provides
    fun provideAuthPersistentStorage(): AuthPersistentStorage =
        AuthPersistentStorageLocalProperties()

    @Provides
    @Named(MOVIES_API)
    fun provideOkHttpMoviesClient(
        @ApplicationContext context: Context,
        authPersistenceStorage: AuthPersistentStorage,
    ): OkHttpClient = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, cacheSize))
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor(authPersistenceStorage))
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(OfflineCacheInterceptor(context))
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY),
            )
            .build()
    } else {
        OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, cacheSize))
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(OfflineCacheInterceptor(context))
            .addInterceptor(AuthorizationInterceptor(authPersistenceStorage)).build()
    }

    @Provides
    @Named(NOW_PLAYING_API)
    fun provideNowPlayingRetrofit(@Named(MOVIES_API) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    contentType,
                ),
            )
            .baseUrl(NOW_PLAYING_API_URL)
            .build()

    @Provides
    @Named(SEARCH_API)
    fun provideSearchRetrofit(@Named(MOVIES_API) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    contentType,
                ),
            )
            .baseUrl(SEARCH_API_URL)
            .build()

    @Provides
    internal fun provideTheMovieDbApi(@Named(NOW_PLAYING_API) retrofit: Retrofit) =
        retrofit.create(TheMovieDbApi::class.java)

    @Provides
    internal fun provideTheMovieDbSearchApi(@Named(SEARCH_API) retrofit: Retrofit) =
        retrofit.create(TheMovieDbSearchApi::class.java)

    @Provides
    @Singleton
    fun provideNetworkResponseMapper(): NetworkResponseMapper =
        NetworkResponseRetrofitMapper()
}
