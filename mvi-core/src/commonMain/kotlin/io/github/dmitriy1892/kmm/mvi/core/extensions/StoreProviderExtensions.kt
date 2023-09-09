package io.github.dmitriy1892.kmm.mvi.core.extensions

import io.github.dmitriy1892.kmm.utils.coroutines.WrappedFlow
import io.github.dmitriy1892.kmm.utils.coroutines.WrappedStateFlow
import io.github.dmitriy1892.kmm.mvi.core.Store
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import io.github.dmitriy1892.kmm.mvi.core.annotation.KmmMviDsl
import io.github.dmitriy1892.kmm.mvi.core.model.StoreContext
import kotlinx.coroutines.Job

val <State: Any, SideEffect: Any> StoreProvider<State, SideEffect>.stateFlow: WrappedStateFlow<State>
    get() = this.store.stateFlow

val <State: Any, SideEffect: Any> StoreProvider<State, SideEffect>.sideEffectFlow: WrappedFlow<SideEffect>
    get() = this.store.sideEffectFlow

/**
 * DSL function for processing a user intent.
 *
 * @param block block that contains a logic for user event reaction.
 *
 * @return [Job].
 *
 * Sample of usage:
 * ```
 * fun changeCounter(currentValue: Int) = intent {
 *     sendSideEffect(CounterSideEffect.ShowIncrementStartedToast)
 *
 *     delay(3000L) // long-running task simulating
 *
 *     reduceState { state -> state.copy(incrementValue = currentValue) }
 *
 *     sendSideEffect(CounterSideEffect.ShowIncrementFinishedToast)
 * }
 * ```
 */
@KmmMviDsl
fun <State: Any, SideEffect: Any> StoreProvider<State, SideEffect>.intent(
    block: suspend StoreContext<State, SideEffect>.() -> Unit
): Job = this.store.intent(block)

/**
 * Function for processing a user intent that can be used inside another suspend function.
 *
 * WARNING: this function is not calls on the [Store.scope] context by default!!!
 *
 * @param block block that contains a logic for user event reaction.
 */
suspend inline fun <State: Any, SideEffect: Any> StoreProvider<State, SideEffect>.suspendIntent(
    crossinline block: suspend StoreContext<State, SideEffect>.() -> Unit
): Unit = this.store.suspendIntent(block)