package io.github.dmitriy1892.kmm.mvi.compose.multiplatform

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectSideEffectWithLifecycle(onEach: @Composable (T) -> Unit)  {
    var sideEffect by remember { mutableStateOf<T?>(null) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(null) {
        this@collectSideEffectWithLifecycle
            .flowWithLifecycle(lifecycle)
            .onEach { sideEffect = it }
            .launchIn(this)
    }

    sideEffect?.let {
        onEach(it)
        sideEffect = null
    }
}