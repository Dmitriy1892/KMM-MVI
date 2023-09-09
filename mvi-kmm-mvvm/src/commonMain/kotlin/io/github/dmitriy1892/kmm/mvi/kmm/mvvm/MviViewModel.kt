package io.github.dmitriy1892.kmm.mvi.kmm.mvvm

import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.extensions.store
import io.github.dmitriy1892.kmm.mvvm.core.BaseViewModel

abstract class MviViewModel<State: Any, SideEffect: Any>(
    initialState: State
) : BaseViewModel(), StoreProvider<State, SideEffect> {

    @Suppress("LeakingThis")
    override val store: Store<State, SideEffect> = viewModelScope.store(initialState)

    override fun onCleared() {
        super.onCleared()
        store.cancel()
    }
}