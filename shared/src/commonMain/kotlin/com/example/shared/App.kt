package com.example.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shared.network.ApiClient
import kotlinx.coroutines.launch

@Composable
fun App(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf("Presiona el botón para probar la conexión") }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("👋 Hello from Shared KMP Code!")
            Text("Greetings from ${platform()}!")

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                scope.launch {
                    try {
                        val response = ApiClient.testConnection(serverUrl())
                        result = "✅ $response"
                    } catch (e: Exception) {
                        result = "❌ Error: ${e.message}"
                    }
                }
            }) {
                Text("Probar conexión")
            }

            Spacer(Modifier.height(16.dp))
            Text(result)
        }
    }
}
