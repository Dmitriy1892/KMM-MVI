package io.github.dmitriy1892.kmm.mvi.android.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.annotation.KmmMviDsl
import io.github.dmitriy1892.kmm.mvi.core.extensions.store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@KmmMviDsl
fun <State: Any, SideEffect: Any> ViewModel.store(initialState: State): Store<State, SideEffect> =
    this.viewModelScope.store(initialState)

val <State: Any, SideEffect: Any> StoreProvider<State, SideEffect>.stateFlow: StateFlow<State>
    get() = this.store.stateFlow

val <State: Any, SideEffect: Any> StoreProvider<State, SideEffect>.sideEffectFlow: Flow<SideEffect>
    get() = this.store.sideEffectFlow