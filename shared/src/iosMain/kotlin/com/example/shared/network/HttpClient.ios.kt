package com.example.shared.network

import io.ktor.client.HttpClient

actual fun createHttpClient(): HttpClient {
    // Implementación dummy para evitar error de compilación
    // (cambiarla más adelante cuando se agregue soporte real para iOS)
    return HttpClient()
}
