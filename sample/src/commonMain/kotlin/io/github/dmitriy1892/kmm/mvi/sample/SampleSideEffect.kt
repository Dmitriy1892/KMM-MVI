package io.github.dmitriy1892.kmm.mvi.sample

sealed class SampleSideEffect {

    object ShowIncrementStarted : SampleSideEffect()

    object ShowIncrementFinished : SampleSideEffect()
}