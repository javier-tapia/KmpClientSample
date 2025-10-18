package com.example.shared

expect fun platform(): String

/**
 * Devuelve la URL base del servidor para la plataforma actual.
 * - Desktop: localhost
 * - Android: emulador -> 10.0.2.2, dispositivo físico -> IP de la PC
 * - iOS: localhost (dummy)
 */
expect fun serverUrl(): String
