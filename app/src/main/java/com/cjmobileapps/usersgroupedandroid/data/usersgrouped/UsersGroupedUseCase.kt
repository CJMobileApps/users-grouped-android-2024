package com.cjmobileapps.usersgroupedandroid.data.usersgrouped

import com.cjmobileapps.usersgroupedandroid.data.model.UsersGrouped
import com.cjmobileapps.usersgroupedandroid.data.model.toUsersGrouped
import com.cjmobileapps.usersgroupedandroid.util.onError
import com.cjmobileapps.usersgroupedandroid.util.onSuccess
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class UsersGroupedUseCase(
    private val usersGroupedRepository: UsersGroupedRepository,
) {
    private val tag = UsersGroupedUseCase::class.java.simpleName

    suspend fun getUsersFromDB(onUsersResponse: (UsersGrouped) -> Unit, onError: () -> Unit) {
        try {
            usersGroupedRepository.getAllUsersFlow().collectLatest { users ->
                onUsersResponse(users.toUsersGrouped())
            }
        } catch (e: Exception) {
            Timber.tag(tag)
                .e("usersGroupedRepository.getAllUsersFlow() error occurred: $e \\n ${e.message}")
            onError.invoke()
        }
    }

    suspend fun fetchUsersApi(onError: () -> Unit) {
        usersGroupedRepository.getAllUsers()
            .onSuccess { users ->
                    try {
                        usersGroupedRepository.createAllUsersToDB(users)
                    } catch (e: Exception) {
                        Timber.tag(tag)
                            .e("usersGroupedRepository.createAllUsersToDB() error occurred: $e \\n ${e.message}")
                        onError.invoke()
                    }
            }
            .onError { statusCode, error ->
                Timber.tag(tag)
                    .e("usersGroupedRepository.getAllUsers() error occurred: statusCode: $statusCode \\n $error")
                onError.invoke()
            }
    }
}
