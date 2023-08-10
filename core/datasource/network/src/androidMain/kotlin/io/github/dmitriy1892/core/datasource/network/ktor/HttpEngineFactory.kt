package io.github.dmitriy1892.core.datasource.network.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android

actual class HttpEngineFactory actual constructor() {

    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = Android
}