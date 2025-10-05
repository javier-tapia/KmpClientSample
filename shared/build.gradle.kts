import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    // Define the JVM toolchain. This ensures consistent Java versions.
    jvmToolchain(17)

    // Android target
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // Define a JVM target for your Desktop app
    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // iOS targets
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Dependencies for shared UI and logic
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor engine for Android
                implementation(libs.ktor.client.okhttp)
            }
        }
        val desktopMain by getting {
            // Tell Gradle this source set exists and depends on commonMain
            dependsOn(commonMain)
            dependencies {
                // Compose dependency for Desktop
                implementation(compose.desktop.currentOs)
                // Ktor engine for Desktop
                implementation(libs.ktor.client.cio)
            }
        }
        // 1. Get a reference to the iosMain source set
        val iosMain by creating {
            // 2. Explicitly link it to commonMain
            dependsOn(commonMain)
        }

        // 3. Link each specific iOS target to iosMain
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.example.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
