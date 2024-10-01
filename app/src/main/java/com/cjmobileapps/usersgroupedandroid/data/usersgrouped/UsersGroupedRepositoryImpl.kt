package com.cjmobileapps.usersgroupedandroid.data.usersgrouped

import com.cjmobileapps.usersgroupedandroid.data.datasource.UsersGroupedApiDataSource
import com.cjmobileapps.usersgroupedandroid.data.datasource.UsersGroupedLocalDataSource
import com.cjmobileapps.usersgroupedandroid.data.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UsersGroupedRepositoryImpl(
    private val usersGroupedApiDataSource: UsersGroupedApiDataSource,
    private val usersGroupedLocalDataSource: UsersGroupedLocalDataSource,
): UsersGroupedRepository {
    override suspend fun getAllUsers():Response<List<User>> =
        usersGroupedApiDataSource.getUsers()

    override suspend fun getAllUsersFlow(): Flow<List<User>> =
        usersGroupedLocalDataSource.getAllUsersFlow()

    override suspend fun createAllUsersToDB(users: List<User>) =
        usersGroupedLocalDataSource.createAllUsersToDB(users)
}
