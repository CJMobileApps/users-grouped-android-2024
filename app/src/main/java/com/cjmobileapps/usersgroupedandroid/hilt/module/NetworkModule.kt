package com.cjmobileapps.usersgroupedandroid.hilt.module

import android.content.Context
import com.cjmobileapps.usersgroupedandroid.BuildConfig
import com.cjmobileapps.usersgroupedandroid.network.NetworkConstants.HTTP_CACHE_DIR
import com.cjmobileapps.usersgroupedandroid.network.NetworkConstants.HTTP_CACHE_SIZE
import com.cjmobileapps.usersgroupedandroid.network.UsersGroupedApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun httpCacheDirectory(
        @ApplicationContext context: Context,
    ): File {
        return File(context.cacheDir, HTTP_CACHE_DIR)
    }

    @Singleton
    @Provides
    fun cache(httpCacheDirectory: File): Cache {
        return Cache(httpCacheDirectory, HTTP_CACHE_SIZE)
    }

    @Singleton
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun usersGroupedApi(retrofit: Retrofit): UsersGroupedApi {
        return retrofit.create(UsersGroupedApi::class.java)
    }
}
