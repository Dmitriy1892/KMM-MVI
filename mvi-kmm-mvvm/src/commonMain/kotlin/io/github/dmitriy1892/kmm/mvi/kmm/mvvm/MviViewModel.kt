package io.github.dmitriy1892.kmm.mvi.kmm.mvvm

import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.extensions.store
import io.github.dmitriy1892.kmm.mvvm.core.BaseViewModel
import io.github.dmitriy1892.kmm.utils.coroutines.WrappedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.WrappedStateFlow

abstract class MviViewModel<State: Any, SideEffect: Any>
    : BaseViewModel(), StoreProvider<State, SideEffect> {

    protected abstract val initialState: State

    override val store: Store<State, SideEffect> by lazy { viewModelScope.store(initialState) }

    val stateFlow: WrappedStateFlow<State> by lazy { store.stateFlow }

    val sideEffectFlow: WrappedFlow<SideEffect> by lazy { store.sideEffectFlow }

    override fun onCleared() {
        super.onCleared()
        store.cancel()
    }
}