package io.github.dmitriy1892.app

import androidx.lifecycle.ViewModel
import io.github.dmitriy1892.kmm.mvi.android.mvvm.store
import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.extensions.intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(), StoreProvider<MainState, MainSideEffect> {

    override val store: Store<MainState, MainSideEffect> = store(MainState(0))

    fun incrementValue(incrementValue: Int) = intent {
        sendSideEffect(MainSideEffect.ShowIncrementStarted)

        reduceState { state -> state.copy(isProgress = true) }

        withContext(Dispatchers.IO) { delay(5000) }

        reduceState { state ->
            state.copy(
                currentValue = state.currentValue + incrementValue,
                isProgress = false
            )
        }

        sendSideEffect(MainSideEffect.ShowIncrementFinished)
    }
}

data class MainState(
    val currentValue: Int = 0,
    val isProgress: Boolean = false
)

sealed interface MainSideEffect {

    object ShowIncrementStarted : MainSideEffect

    object ShowIncrementFinished : MainSideEffect
}