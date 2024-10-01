package com.cjmobileapps.usersgroupedandroid.util

import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext

object TestCoroutineDispatchers : CoroutineDispatchers {
    override val io: CoroutineContext = Dispatchers.Unconfined

    override val ioExecutor: Executor = Dispatchers.Unconfined.asExecutor()

    override val main: CoroutineContext = Dispatchers.Unconfined
}
