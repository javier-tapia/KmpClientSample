package com.example.shared.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

expect fun createHttpClient(): HttpClient

object ApiClient {
    private val client = createHttpClient()

    suspend fun testConnection(baseUrl: String): String {
        return client.get(baseUrl).bodyAsText()
    }
}
