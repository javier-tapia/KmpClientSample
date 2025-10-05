package com.example.desktopapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.shared.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP Desktop Sample"
    ) {
        // Call the common UI function from the :shared module
        App()
    }
}
