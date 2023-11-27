package com.alterjuice.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RestartableJob(
    launchBlock: suspend CoroutineScope.(Nothing?) -> Unit
): RestartableJobArgs<Nothing?>(launchBlock) {

    constructor(
        launchBlock: suspend CoroutineScope.(Nothing?) -> Unit,
        onCompletion: () -> Unit
    ): this(launchBlock) {
        this.onCompletion = onCompletion
    }

    fun restart(
        scope: CoroutineScope,
        dispatcher: CoroutineContext = Dispatchers.Default,
        handler: CompletionHandler = {}
    ) = restart(scope, dispatcher, value = null, handler)

    suspend fun restartJoin(
        scope: CoroutineScope,
        dispatcher: CoroutineContext = Dispatchers.Default,
    ) = restartJoin(scope, dispatcher, value = null)
}


open class RestartableJobArgs<T>(
    protected val launchBlock: suspend CoroutineScope.(T) -> Unit
) {

    protected var onCompletion: () -> Unit = {}

    constructor(
        launchBlock: suspend CoroutineScope.(T?) -> Unit,
        onCompletion: () -> Unit
    ): this(launchBlock){
        this.onCompletion = onCompletion
    }

    protected var job: Job? = null
    val currentJob get() = job

    /**
     * Method is used to start/restart with cancellation job-block with
     * given scope and Dispatcher
     * */

    private fun restart(
        scope: CoroutineScope,
        dispatcher: CoroutineContext = Dispatchers.Default,
        value: T,
    ): Job {
        cancel()
        return scope.launch(dispatcher + SupervisorJob()) {
            launchBlock.invoke(this, value)
        }.also {
            it.invokeOnCompletion { onCompletion() }
            job = it
        }
    }
    fun restart(
        scope: CoroutineScope,
        dispatcher: CoroutineContext = Dispatchers.Default,
        value: T,
        handler: CompletionHandler = {}
    ) {
        restart(scope, dispatcher, value).also {
            it.invokeOnCompletion(handler)
        }
    }

    suspend fun restartJoin(
        scope: CoroutineScope,
        dispatcher: CoroutineContext = Dispatchers.Default,
        value: T
    ) {
        restart(scope, dispatcher, value).also {
            it.join()
        }
    }

    fun cancel() = job?.cancel()

    fun cancelAndClear() {
        cancel()
        job = null
    }
}
