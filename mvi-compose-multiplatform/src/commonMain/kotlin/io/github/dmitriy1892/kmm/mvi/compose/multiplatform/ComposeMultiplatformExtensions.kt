package io.github.dmitriy1892.kmm.mvi.compose.multiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import io.github.dmitriy1892.kmm.mvi.compose.multiplatform.essenty.flowWithLifecycle
import io.github.dmitriy1892.kmm.mvi.compose.multiplatform.essenty.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
val LocalLifecycleOwner = compositionLocalOf<LifecycleOwner> { error("No LifecycleOwner state provided") }

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> = collectAsStateWithLifecycle(
    initialValue = value,
    lifecycleOwner = lifecycleOwner,
    minActiveState = minActiveState,
    context = context
)

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Composable
fun <T> Flow<T>.collectAsStateWithLifecycle(
    initialValue: T,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> {
    return produceState(initialValue, this, lifecycleOwner, minActiveState, context) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            if (context == EmptyCoroutineContext) {
                this@collectAsStateWithLifecycle.collect { this@produceState.value = it }
            } else withContext(context) {
                this@collectAsStateWithLifecycle.collect { this@produceState.value = it }
            }
        }
    }
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Composable
fun <T> Flow<T>.collectSideEffectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEach: @Composable (T) -> Unit
) {
    var sideEffect by remember { mutableStateOf<T?>(null) }

    LaunchedEffect(null) {
        this@collectSideEffectWithLifecycle
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .onEach {
                sideEffect = it
            }
            .launchIn(this)
    }

    sideEffect?.let {
        onEach(it)
        sideEffect = null
    }
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Composable
fun <T> Flow<T>.collectSideEffect(onEach: @Composable (T) -> Unit)  {
    var sideEffect by remember { mutableStateOf<T?>(null) }

    LaunchedEffect(null) {
        this@collectSideEffect
            .onEach { sideEffect = it }
            .launchIn(this)
    }

    sideEffect?.let {
        onEach(it)
        sideEffect = null
    }
}