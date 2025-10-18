<h1>KMP Client Sample</h1>

Este es un proyecto de ejemplo de Kotlin Multiplatform (KMP) que demuestra una arquitectura cliente-servidor.

<!-- TOC -->
  * [Descripción General](#descripción-general)
  * [Arquitectura](#arquitectura)
  * [Interacción de los Módulos](#interacción-de-los-módulos)
  * [Estructura de los Módulos: ¿Por qué no todo en `shared`?](#estructura-de-los-módulos-por-qué-no-todo-en-shared)
  * ["Targets" en el Módulo `shared`](#targets-en-el-módulo-shared)
  * [Relación entre "Target" y "Source Set"](#relación-entre-target-y-source-set)
<!-- TOC -->

---


## Descripción General

El proyecto está estructurado para maximizar la reutilización de código entre diferentes plataformas (Android y Desktop, iOS dummy por ahora) utilizando Kotlin Multiplatform. La interfaz de usuario se construye con Compose Multiplatform, lo que permite compartir también el código de la UI.

## Arquitectura

La arquitectura del proyecto se divide en los siguientes módulos:

-   **`shared`**: Este es el corazón del proyecto KMP. Contiene la lógica de negocio, el acceso a datos y los modelos que se comparten entre todas las plataformas. Aquí es donde reside la mayor parte del código, escrito en Kotlin común.

-   **`androidApp`**: La aplicación para Android. Este módulo consume el código del módulo `shared` y proporciona la implementación específica de la plataforma para la interfaz de usuario de Android. La UI está escrita en Jetpack Compose.

-   **`desktopApp`**: La aplicación de escritorio (JVM). Al igual que la aplicación de Android, este módulo consume el código del módulo `shared`. La interfaz de usuario se crea con Compose for Desktop, permitiendo una experiencia de usuario consistente en diferentes sistemas operativos de escritorio.

## Interacción de los Módulos

1.  El módulo **`shared`** define las interfaces y la lógica de negocio principal (por ejemplo, obtener datos de una API remota, manejar el estado de la aplicación).
2.  Los módulos **`androidApp`** y **`desktopApp`** dependen del módulo `shared`.
3.  Cada aplicación de plataforma (`androidApp` y `desktopApp`) inicializa la capa compartida y utiliza sus componentes para construir la interfaz de usuario y manejar las interacciones del usuario.
4.  Compose Multiplatform permite que gran parte del código de la interfaz de usuario se defina en el código común (dentro de `shared/src/commonMain/kotlin`) y luego se renderice de forma nativa en cada plataforma de destino.

Este enfoque permite un desarrollo más rápido y consistente, ya que la lógica de negocio y, en gran medida, la UI, solo necesitan escribirse una vez.

## Estructura de los Módulos: ¿Por qué no todo en `shared`?

Aunque el módulo `shared` contiene la mayor parte del código, los módulos específicos de la plataforma como `androidApp` y `desktopApp` son esenciales por varias razones:

-   **Punto de Entrada de la Aplicación**: Cada plataforma tiene una forma diferente de iniciar una aplicación. El módulo `androidApp` contiene el `Activity` de Android y el `AndroidManifest.xml` necesarios para lanzar la aplicación. De manera similar, `desktopApp` contiene la función `main` que crea la ventana de la aplicación de escritorio. El módulo `shared` está configurado como una librería, no como una aplicación ejecutable.

-   **Dependencias y SDKs específicos de la plataforma**: Cada módulo de aplicación puede incluir dependencias que solo son relevantes para su plataforma. Por ejemplo, `androidApp` incluye las librerías de Jetpack específicas de Android, mientras que `desktopApp` podría incluir librerías para interactuar con el sistema de archivos del escritorio.

-   **Empaquetado**: El proceso de empaquetado es radicalmente diferente para cada plataforma. El módulo `androidApp` se compila en un archivo APK o AAB, mientras que `desktopApp` se puede empaquetar como un JAR ejecutable o un instalador nativo (MSI, DMG, DEB). Los módulos de aplicación se encargan de esta configuración de compilación específica.

## "Targets" en el Módulo `shared`

En un proyecto Kotlin Multiplatform, un "target" representa una plataforma específica para la que se compilará el código del módulo `shared`.

-   **¿Qué son?**: Son, en esencia, los destinos de compilación. Ejemplos comunes son `android`, `jvm` (para la aplicación de escritorio), `ios`, `js` (JavaScript), etc.

-   **¿Cómo se definen?**: Los targets se declaran en el archivo `build.gradle.kts` del módulo `shared`, dentro del bloque `kotlin { ... }`. Para cada target, se puede especificar sus dependencias y configuraciones de compilación.

-   **¿Para qué sirven?**: Los targets permiten estructurar el código compartido a través de los ***source sets***.
    -   **`commonMain`**: Contiene el código que es 100% independiente de la plataforma y funciona en todos los targets.
    -   **`androidMain`**, **`desktopMain`**, etc.: Contienen la implementación específica de la plataforma. A menudo, esto se usa para implementar declaraciones `expect` definidas en `commonMain` con su correspondiente `actual` en el _source set_ de la plataforma. Esto permite que el código común "espere" una funcionalidad que será proporcionada por cada plataforma de destino.

Por ejemplo, se podría tener una `expect fun getPlatformName(): String` en `commonMain`, y luego en `androidMain` una `actual fun getPlatformName(): String = "Android"`, y en `desktopMain` una `actual fun getPlatformName(): String = "Desktop"`. El código común puede llamar a `getPlatformName()` sin saber en qué plataforma se está ejecutando.

## Relación entre "Target" y "Source Set"

No son lo mismo, pero su relación es fundamental para entender KMP:

-   Un **Target** es el **destino de la compilación**. Le dice a Kotlin ***para qué plataforma compilar*** el código (ej: `android`, `jvm`, `iosArm64`).

-   Un **Source Set** es un **conjunto de carpetas con código fuente**. Le dice a Kotlin ***qué código compilar*** para uno o más targets.

La regla es simple: **cuando se define un _target_, el plugin de Kotlin crea automáticamente un _source set_ para él**. Por ejemplo, el _target_ `android()` crea el _source set_ `androidMain`. El código que se coloque en la carpeta `shared/src/androidMain/kotlin` solo será visible y se compilará para el _target_ de Android.

El _source set_ `commonMain` es especial: su código se compila e incluye en **todos** los _targets_ que se hayan definido. Por eso es el lugar para el código compartido.

En resumen:

> Los **Targets** definen el "qué" (qué plataformas). Los **Source Sets** organizan el "dónde" (dónde vive el código para esas plataformas).
