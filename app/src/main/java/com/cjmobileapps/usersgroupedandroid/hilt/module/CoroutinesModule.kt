package com.cjmobileapps.usersgroupedandroid.hilt.module

import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesModule {
    @Singleton
    @Provides
    fun coroutinesDispatchers(): CoroutineDispatchers {
        return CoroutineDispatchersImpl
    }
}
