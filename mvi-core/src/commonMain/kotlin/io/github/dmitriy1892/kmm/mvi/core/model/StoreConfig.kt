package io.github.dmitriy1892.kmm.mvi.core.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel

/**
 * StoreConfig - class with [io.github.dmitriy1892.kmm.mvi.core.Store] configuration.
 * Used for [io.github.dmitriy1892.kmm.mvi.core.Store.scope] configuring.
 */
data class StoreConfig(
    val eventsDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    val exceptionHandler: CoroutineExceptionHandler? = null
)