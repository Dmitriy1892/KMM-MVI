package io.github.dmitriy1892.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import io.github.dmitriy1892.kmm.mvi.core.extensions.sideEffectFlow
import io.github.dmitriy1892.kmm.mvi.core.extensions.stateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val stateHolder = viewModel.store.stateFlow.collectAsState()

                    LaunchedEffect(key1 = null) {
                        viewModel.stateFlow
                            .flowWithLifecycle(lifecycle)
                            .onEach {  }
                            .launchIn(this)

                        viewModel.sideEffectFlow
                            .flowWithLifecycle(lifecycle)
                            .onEach(::renderSideEffect)
                            .launchIn(this)
                    }

                    GreetingView(stateHolder.value) {
                        val currentId = viewModel.store.stateFlow.value.id
                        viewModel.incrementId(currentId + 1)
                    }
                }
            }
        }
    }

    private fun renderSideEffect(sideEffect: MainSideEffect) {
        val text = when (sideEffect) {
            MainSideEffect.StartLoadingToast -> "Loading started"
            MainSideEffect.EndLoadingToast -> "Loading finished"
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun GreetingView(
    state: MainState,
    onButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Value: ${state.id}")
        Button(
            enabled = state.isLoading.not(),
            onClick = { onButtonClick() }
        ) {
            Text(text = "Increment")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView(MainState(0)) {}
    }
}
