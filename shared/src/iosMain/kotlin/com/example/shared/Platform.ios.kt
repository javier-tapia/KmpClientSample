package com.example.shared

actual fun platform() = "iOS"
// Implementación dummy para evitar error de compilación
// (cambiarla más adelante cuando se agregue soporte real para iOS)
actual fun serverUrl() = "http://localhost:8080"
