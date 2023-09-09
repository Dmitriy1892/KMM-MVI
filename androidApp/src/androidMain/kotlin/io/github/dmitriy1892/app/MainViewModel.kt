package io.github.dmitriy1892.app

import androidx.lifecycle.ViewModel
import io.github.dmitriy1892.kmm.mvi.android.mvvm.store
import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.extensions.intent
import kotlinx.coroutines.delay

class MainViewModel : ViewModel(), StoreProvider<MainState, MainSideEffect> {

    override val store: Store<MainState, MainSideEffect> = store(MainState(0))

    fun incrementId(newId: Int) = intent {
        sendSideEffect(MainSideEffect.StartLoadingToast)

        reduceState { state -> state.copy(isLoading = true) }

        delay(3000)

        reduceState { state -> state.copy(id = newId, isLoading = false) }

        println("State: $state")

        sendSideEffect(MainSideEffect.EndLoadingToast)
    }
}

data class MainState(
    val id: Int,
    val isLoading: Boolean = false
)

sealed interface MainSideEffect {

    object StartLoadingToast : MainSideEffect

    object EndLoadingToast : MainSideEffect
}