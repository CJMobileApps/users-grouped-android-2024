package com.cjmobileapps.usersgroupedandroid.util


import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    val body = this.body()
    if (this.isSuccessful && body != null) action(body)
    return this
}

inline fun <T : Any> Response<T>.onError(action: (String?, Int) -> Unit): Response<T> {
    val errorBody = this.errorBody()
    if (!this.isSuccessful) {
        action(errorBody?.string(), this.code())
    }
    return this
}

suspend fun <T : Any> withContextApiWrapper(
    coroutineContext: CoroutineContext,
    requestFunc: suspend () -> Deferred<Response<T>>,
): Response<T> {
    return withContext(coroutineContext) {
            requestFunc.invoke()
                .await()
        }
}
