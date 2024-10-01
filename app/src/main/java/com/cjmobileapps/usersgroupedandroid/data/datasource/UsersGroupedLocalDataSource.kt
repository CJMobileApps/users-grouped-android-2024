package com.cjmobileapps.usersgroupedandroid.data.datasource

import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.usersgroupedandroid.data.model.User
import com.cjmobileapps.usersgroupedandroid.room.UsersGroupedDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UsersGroupedLocalDataSource(
    private val usersGroupedDao: UsersGroupedDao,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getAllUsersFlow(): Flow<List<User>> {
        return withContext(coroutineDispatchers.io) {
            usersGroupedDao.getAllUsers()
        }
    }

    suspend fun createAllUsersToDB(users: List<User>) {
        withContext(coroutineDispatchers.io) {
            usersGroupedDao.deleteAllUsers()
            usersGroupedDao.insertAllUsers(users)
        }
    }
}
