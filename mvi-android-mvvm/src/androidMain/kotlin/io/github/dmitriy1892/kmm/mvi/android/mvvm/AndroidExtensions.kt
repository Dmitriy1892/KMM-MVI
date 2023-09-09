package io.github.dmitriy1892.kmm.mvi.android.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.annotation.KmmMviDsl
import io.github.dmitriy1892.kmm.mvi.core.extensions.store
import io.github.dmitriy1892.kmm.mvi.core.model.StoreConfig

@KmmMviDsl
fun <State: Any, SideEffect: Any> ViewModel.store(
    initialState: State,
    storeConfig: StoreConfig = StoreConfig()
): Store<State, SideEffect> = viewModelScope.store(initialState, storeConfig)