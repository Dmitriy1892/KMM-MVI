package io.github.dmitriy1892.kmm.mvi.sample

import io.github.dmitriy1892.kmm.mvi.core.extensions.intent
import io.github.dmitriy1892.kmm.mvi.kmm.mvvm.MviViewModel
import io.github.dmitriy1892.kmm.utils.coroutines.CoroutineDispatcherProviderImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SampleViewModel : MviViewModel<SampleState, SampleSideEffect>() {

    override val initialState: SampleState = SampleState()

    fun incrementValue(incrementValue: Int) = intent {
        sendSideEffect(SampleSideEffect.ShowIncrementStarted)

        reduceState { state -> state.copy(isProgress = true) }

        withContext(CoroutineDispatcherProviderImpl().io) { delay(5000) }

        reduceState { state ->
            state.copy(
                currentValue = state.currentValue + incrementValue,
                isProgress = false
            )
        }

        sendSideEffect(SampleSideEffect.ShowIncrementFinished)
    }
}