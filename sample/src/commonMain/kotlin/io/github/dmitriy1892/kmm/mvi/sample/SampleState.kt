package io.github.dmitriy1892.kmm.mvi.sample

data class SampleState(
    val currentValue: Int = 0,
    val isProgress: Boolean = false
)