package io.github.dmitriy1892.kmm.mvi.core.internal

import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.model.StoreConfig
import io.github.dmitriy1892.kmm.mvi.core.model.StoreContext
import io.github.dmitriy1892.kmm.utils.coroutines.flow.EventMutableSharedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.flow.WrappedSharedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.flow.WrappedStateFlow
import io.github.dmitriy1892.kmm.utils.coroutines.flow.asWrappedSharedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.flow.asWrappedStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
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
    override val stateFlow: WrappedStateFlow<State> = _stateFlow.asWrappedStateFlow()

    private val _sideEffectFlow = EventMutableSharedFlow<SideEffect>()
    override val sideEffectFlow: WrappedSharedFlow<SideEffect> =
        _sideEffectFlow.asWrappedSharedFlow()

    private val scopeContext = StoreContext<State, SideEffect>(
        sendSideEffect = { sideEffect ->
            _sideEffectFlow.emit(sideEffect)
        },
        getState = { _stateFlow.value },
        reduceState = { reducer -> _stateFlow.update(reducer) }
    )

    override suspend fun processIntent(intent: suspend StoreContext<State, SideEffect>.() -> Unit) {
        intent.invoke(scopeContext)
    }
}