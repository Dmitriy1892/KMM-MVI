package io.github.dmitriy1892.kmm.mvi.core.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel

/**
 * StoreConfig - class with [io.github.dmitriy1892.mvi.core.Store] configuration.
 * Used for [io.github.dmitriy1892.mvi.core.Store.scope]
 * and [io.github.dmitriy1892.mvi.core.Store.sideEffectFlow] configuring.
 */
data class StoreConfig(
    val sideEffectBufferSize: Int = Channel.BUFFERED,
    val eventsDispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
    val exceptionHandler: CoroutineExceptionHandler? = null
)