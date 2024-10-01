package com.cjmobileapps.usersgroupedandroid.data.usersgrouped

import com.cjmobileapps.usersgroupedandroid.data.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UsersGroupedRepository {
    suspend fun getAllUsers(): Response<List<User>>

    suspend fun getAllUsersFlow(): Flow<List<User>>

    suspend fun createAllUsersToDB(users: List<User>)
}
