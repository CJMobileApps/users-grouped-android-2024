package com.cjmobileapps.usersgroupedandroid.room

import androidx.room.TypeConverter
import com.cjmobileapps.usersgroupedandroid.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toUserList(value: String?): List<User> {
        if (value.isNullOrEmpty() || value == "null") return emptyList()
        val type = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromUserList(value: List<User>): String {
        return Gson().toJson(value)
    }
}
