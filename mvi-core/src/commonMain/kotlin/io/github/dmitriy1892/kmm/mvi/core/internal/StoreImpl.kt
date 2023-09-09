package io.github.dmitriy1892.kmm.mvi.core.internal

import io.github.dmitriy1892.kmm.utils.coroutines.WrappedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.WrappedStateFlow
import io.github.dmitriy1892.kmm.utils.coroutines.asWrappedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.asWrappedStateFlow
import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.model.StoreConfig
import io.github.dmitriy1892.kmm.mvi.core.model.StoreContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

/**
 * Default implementation of [Store].
 * Used in extension functions for simplifying a [Store] creation process.
 */
internal class StoreImpl<State: Any, SideEffect: Any>(
    initialState: State,
    parentScope: CoroutineScope,
    override val config: StoreConfig
) : Store<State, SideEffect> {

    override val scope = (parentScope + config.eventsDispatcher).apply {
        config.exceptionHandler?.let { coroutineExceptionHandler ->
            plus(coroutineExceptionHandler)
        }
    }

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow: WrappedStateFlow<State>
        get() = _stateFlow.asWrappedStateFlow()

    private val sideEffectChannel = Channel<SideEffect>(config.sideEffectBufferSize)
    override val sideEffectFlow: WrappedFlow<SideEffect>
        get() = sideEffectChannel.receiveAsFlow().asWrappedFlow()

    private val scopeContext = StoreContext<State, SideEffect>(
        sendSideEffect = { sideEffect ->
            sideEffectChannel.send(sideEffect)
        },
        getState = { _stateFlow.value },
        reduceState = { reducer -> _stateFlow.update(reducer) }
    )

    override suspend fun processIntent(intent: suspend StoreContext<State, SideEffect>.() -> Unit) {
        intent.invoke(scopeContext)
    }
}