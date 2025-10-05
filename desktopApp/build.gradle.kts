import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    // This tells the desktopApp to use the "desktop" variant of the shared module
    implementation(project(":shared"))
}

// Configure the desktop application
compose.desktop {
    application {
        // Use the fully qualified name from your file
        mainClass = "com.example.desktopapp.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KmpClientSample"
            packageVersion = "1.0.0"
        }
    }
}

// Align the JVM toolchain with the rest of the project
kotlin {
    jvmToolchain(17)
}
