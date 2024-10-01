package com.cjmobileapps.usersgroupedandroid.network

import com.cjmobileapps.usersgroupedandroid.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface UsersGroupedApi {

    @GET("hiring.json")
    fun getUsers(): Deferred<Response<List<User>>>
}
