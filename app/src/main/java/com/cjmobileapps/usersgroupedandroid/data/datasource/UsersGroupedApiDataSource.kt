package com.cjmobileapps.usersgroupedandroid.data.datasource

import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.usersgroupedandroid.data.model.User
import com.cjmobileapps.usersgroupedandroid.network.UsersGroupedApi
import com.cjmobileapps.usersgroupedandroid.util.withContextApiWrapper
import retrofit2.Response

class UsersGroupedApiDataSource(
    private val usersGroupedApi: UsersGroupedApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getUsers(): Response<List<User>> {
        return withContextApiWrapper(coroutineDispatchers.io) {
            usersGroupedApi.getUsers()
        }
    }
}
