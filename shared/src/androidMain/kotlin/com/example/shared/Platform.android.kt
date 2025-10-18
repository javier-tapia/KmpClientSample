package com.example.shared

actual fun platform() = "Android"

/**
 * `10.0.2.2` es la IP especial que usan los **emuladores de Android** para acceder al localhost de la PC (host).
 *
 * Para **dispositivos físicos**, se debe usar la dirección IP local de la computadora en la red.
 *
 * 🔹 **Windows:** ejecutar el comando en el *Símbolo del sistema (CMD)*:
 *
 * ```
 * ipconfig
 * ```
 *
 * Buscar el apartado **"Adaptador de Ethernet"** o **"Adaptador de LAN inalámbrica"** y copiar el valor de:
 *
 * ```
 * Dirección IPv4. . . . . . . . . . . . . . : 192.168.xxx.xxx  ← ESTA ES LA QUE VA
 * ```
 *
 * 🔹 **macOS / Linux:** ejecutar el comando en *Terminal*:
 *
 * ```
 * ifconfig
 * ```
 *
 * Buscar el bloque correspondiente a la interfaz activa:
 * - `en0` o `en1` para Wi-Fi
 * - `eth0` para Ethernet
 *
 * Dentro de ese bloque, copiar el valor que aparece después de `inet`, por ejemplo:
 *
 * ```
 * en0: flags=8863<UP,BROADCAST,SMART,RUNNING,SIMPLEX,MULTICAST> mtu 1500
 *     inet 192.168.1.24 netmask 0xffffff00 broadcast 192.168.1.255
 *                ↑ ESTA ES LA IP LOCAL
 * ```
 *
 * Luego, reemplazar el valor en el código por la IP encontrada:
 *
 * ```kotlin
 * actual fun serverUrl() = "http://192.168.1.24:8080"
 * ```
 */
actual fun serverUrl() =
    if (isEmulator()) {
        "http://10.0.2.2:8080"
    } else {
        // Agregar la IP local
        "http://192.168.xxx.xxx:8080"
    }

/** Detecta si la app corre en un emulador Android */
private fun isEmulator() =
    android.os.Build.FINGERPRINT.startsWith("generic") ||
            android.os.Build.FINGERPRINT.startsWith("unknown") ||
            android.os.Build.MODEL.contains("google_sdk") ||
            android.os.Build.MODEL.contains("Emulator") ||
            android.os.Build.MODEL.contains("Android SDK built for x86")
