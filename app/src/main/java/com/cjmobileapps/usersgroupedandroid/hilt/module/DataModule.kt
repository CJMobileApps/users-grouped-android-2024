package com.cjmobileapps.usersgroupedandroid.hilt.module

import android.content.Context
import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.usersgroupedandroid.data.datasource.UsersGroupedApiDataSource
import com.cjmobileapps.usersgroupedandroid.data.datasource.UsersGroupedLocalDataSource
import com.cjmobileapps.usersgroupedandroid.data.usersgrouped.UsersGroupedRepository
import com.cjmobileapps.usersgroupedandroid.data.usersgrouped.UsersGroupedRepositoryImpl
import com.cjmobileapps.usersgroupedandroid.data.usersgrouped.UsersGroupedUseCase
import com.cjmobileapps.usersgroupedandroid.network.UsersGroupedApi
import com.cjmobileapps.usersgroupedandroid.room.DatabaseFactory
import com.cjmobileapps.usersgroupedandroid.room.UsersGroupedDao
import com.cjmobileapps.usersgroupedandroid.room.UsersGroupedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Singleton
    @Provides
    fun usersGroupedApiDataSource(
        usersGroupedApi: UsersGroupedApi,
        coroutineDispatchers: CoroutineDispatchers,
    ): UsersGroupedApiDataSource {
        return UsersGroupedApiDataSource(
            usersGroupedApi = usersGroupedApi,
            coroutineDispatchers = coroutineDispatchers,
        )
    }

    @Singleton
    @Provides
    fun usersGroupedRepository(
        usersGroupedApiDataSource: UsersGroupedApiDataSource,
        usersGroupedLocalDataSource: UsersGroupedLocalDataSource,
    ): UsersGroupedRepository {
        return UsersGroupedRepositoryImpl(
            usersGroupedApiDataSource = usersGroupedApiDataSource,
            usersGroupedLocalDataSource = usersGroupedLocalDataSource,
        )
    }

    @Singleton
    @Provides
    fun usersGroupedUseCase(usersGroupedRepository: UsersGroupedRepository): UsersGroupedUseCase {
        return UsersGroupedUseCase(
            usersGroupedRepository = usersGroupedRepository
        )
    }

    @Singleton
    @Provides
    fun usersGroupedDatabase(
        @ApplicationContext context: Context,
    ): UsersGroupedDatabase {
        return DatabaseFactory.getDB(context)
    }

    @Singleton
    @Provides
    fun usersGroupedDao(usersGroupedDatabase: UsersGroupedDatabase): UsersGroupedDao {
        return usersGroupedDatabase.usersGroupedDao()
    }

    @Singleton
    @Provides
    fun usersGroupedLocalDataSource(
        usersGroupedDao: UsersGroupedDao,
        coroutineDispatchers: CoroutineDispatchers,
    ): UsersGroupedLocalDataSource {
        return UsersGroupedLocalDataSource(
            usersGroupedDao = usersGroupedDao,
            coroutineDispatchers = coroutineDispatchers,
        )
    }
}
