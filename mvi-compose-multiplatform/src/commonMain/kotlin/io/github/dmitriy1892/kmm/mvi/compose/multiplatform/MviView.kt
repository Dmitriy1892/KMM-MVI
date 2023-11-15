package io.github.dmitriy1892.kmm.mvi.compose.multiplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import io.github.dmitriy1892.kmm.mvi.core.StoreProvider
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Composable
fun <State: Any, SideEffect: Any> MviView(
    modifier: Modifier = Modifier.fillMaxSize(),
    storeProvider: StoreProvider<State, SideEffect>,
    onSideEffect: @Composable (SideEffect) -> Unit,
    content: @Composable (State) -> Unit
) {
    Box(modifier = modifier) {
        storeProvider.store.sideEffectFlow.collectSideEffect(onSideEffect)
        val state = storeProvider.store.stateFlow.collectAsState().value
        content(state)
    }
}