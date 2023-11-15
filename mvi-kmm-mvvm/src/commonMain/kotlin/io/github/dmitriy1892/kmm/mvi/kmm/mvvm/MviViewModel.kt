package io.github.dmitriy1892.kmm.mvi.kmm.mvvm

import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.extensions.store
import io.github.dmitriy1892.kmm.mvi.core.model.StoreConfig
import io.github.dmitriy1892.kmm.mvvm.core.BaseViewModel
import io.github.dmitriy1892.kmm.utils.coroutines.flow.WrappedSharedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.flow.WrappedStateFlow
import kotlinx.coroutines.Dispatchers

abstract class MviViewModel<State: Any, SideEffect: Any>
    : BaseViewModel(), StoreProvider<State, SideEffect> {

    protected abstract val initialState: State

    override val store: Store<State, SideEffect> by lazy {
        viewModelScope.store(
            initialState = initialState,
            storeConfig = StoreConfig(
                eventsDispatcher = Dispatchers.Main.immediate,
                exceptionHandler = getCoroutineExceptionHandler()
            )
        )
    }

    val stateFlow: WrappedStateFlow<State> by lazy { store.stateFlow }

    val sideEffectFlow: WrappedSharedFlow<SideEffect> by lazy { store.sideEffectFlow }

    override fun onCleared() {
        super.onCleared()
        store.cancel()
    }
}