package io.github.dmitriy1892.kmm.mvi.core.model

/**
 * Class for providing a set of functions
 * for [io.github.dmitriy1892.mvi.core.Store]'s [SideEffect]s sending,
 * [State] getting and transformation.
 */
class StoreContext<State: Any, SideEffect: Any>(
    val sendSideEffect: suspend (SideEffect) -> Unit,
    val getState: () -> State,
    val reduceState: suspend ((State) -> State) -> Unit
) {

    val state: State
        get() = getState()
}