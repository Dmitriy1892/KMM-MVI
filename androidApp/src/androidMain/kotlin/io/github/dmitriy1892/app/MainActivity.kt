package io.github.dmitriy1892.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import io.github.dmitriy1892.kmm.mvi.android.mvvm.sideEffectFlow
import io.github.dmitriy1892.kmm.mvi.android.mvvm.stateFlow
import io.github.dmitriy1892.kmm.mvi.sample.SampleSideEffect
import io.github.dmitriy1892.kmm.mvi.sample.SampleViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LaunchedEffect(null) {
                        viewModel.sideEffectFlow
                            .flowWithLifecycle(this@MainActivity.lifecycle)
                            .onEach(::renderSideEffect)
                            .launchIn(this)
                    }
                    val state by viewModel.stateFlow.collectAsState()

                    GreetingView(
                        currentValue = state.currentValue,
                        isProgress = state.isProgress
                    ) { newIncrementValue ->
                        viewModel.incrementValue(newIncrementValue)
                    }
                }
            }
        }
    }

    private fun renderSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            MainSideEffect.ShowIncrementStarted -> showToast("Increment started")
            MainSideEffect.ShowIncrementFinished -> showToast("Increment finished")
        }
    }

    private fun renderSideEffect(sideEffect: SampleSideEffect) {
        when (sideEffect) {
            SampleSideEffect.ShowIncrementStarted -> showToast("Increment started")
            SampleSideEffect.ShowIncrementFinished -> showToast("Increment finished")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun GreetingView(
    currentValue: Int,
    isProgress: Boolean,
    onIncrementClicked: (value: Int) -> Unit
) {
    var input by remember {
        mutableStateOf("0")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Current value: $currentValue")

            TextField(
                value = input,
                label = { Text(text = "Enter increment value") },
                onValueChange = { newValue ->
                    input = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 40.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = isProgress.not()
            )

            Button(
                onClick = {
                    val increment = input.toIntOrNull() ?: 1
                    onIncrementClicked(increment)
                },
                enabled = input.toIntOrNull() != null && isProgress.not()
            ) {
                Text(text = "Increment")
            }
        }

        if (isProgress) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView(1, false) {}
    }
}
