package com.cjmobileapps.usersgroupedandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cjmobileapps.usersgroupedandroid.data.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class UsersGroupedDatabase : RoomDatabase() {
    abstract fun usersGroupedDao(): UsersGroupedDao
}

class DatabaseFactory {
    companion object {
        fun getDB(context: Context): UsersGroupedDatabase {
            return Room.databaseBuilder(
                context,
                UsersGroupedDatabase::class.java,
                "users-grouped-database"
            ).build()
        }
    }
}
