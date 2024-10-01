package com.cjmobileapps.usersgroupedandroid.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cjmobileapps.usersgroupedandroid.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersGroupedDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Insert
    fun insertAllUsers(users: List<User>)

    @Query("DELETE FROM user")
    fun deleteAllUsers()
}
