# RandomApplication

## О приложении

Данное приложение пока что не несет какую-либо идею, но в будущем будет внедрено много
интересного =)

### Стек

**Основные технологии:**

- Kotlin MultiPlatform (KMP) - кросс-платформа Android/iOS
- Compose MultiPlatform (CMP) - UI приложения
- Jetpack Compose Navigation for KMP - навигация между экранами в KMP-проекте

**Библиотеки:**

- Coil - подгрузка картинок из Интернета

  _Примечание:_ для Android реализован через клиент OkHttp

- Napier - кроссплатформенное логирование
- Ktor - кроссплатформенный HTTP-клиент (пока что **добавлен (но не реализован!)** только для iOS)
- Kotlin Serialization - добавление аннотации `@Serializable`

**Плагины:**

- Kotlin Serialization - добавление аннотации `@Serializable`

Далее будут приведены добавленные фрагменты в файлы `libs.versions.toml` и `build.gradle.kts` для модуля `:composeApp`

- `libs.versions.toml`, содержащий только указанные библиотеки:

    ```toml
    [versions]
    androidx-navigation = "2.9.2"
    kotlin = "2.3.0"
    kotlinx-serialization = "1.10.0"
    napierVersion = "2.7.1"
    coilVersion = "3.3.0"
    ktorIOS = "3.4.0"
    
    [libraries]
    androidx-navigation-compose = { module = "org.jetbrains.androidx.navigation:navigation-compose", version.ref = "androidx-navigation" }
    kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }
    napier = { module = "io.github.aakira:napier", version.ref = "napierVersion" }
    coil-compose = { module = "io.coil-kt.coil3:coil-compose", version.ref = "coilVersion" }
    coil-network-android = { module = "io.coil-kt.coil3:coil-network-okhttp", version.ref = "coilVersion" }
    coil-network-ios = { module = "io.coil-kt.coil3:coil-network-ktor3", version.ref = "coilVersion" }
    ktor-ios = { module = "io.ktor:ktor-client-darwin", version.ref = "ktorIOS" }
    
    [plugins]
    kotlinx-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
    ```

- `build.gradle.kts` для модуля `:composeApp`, импортирующий только указанные библиотеки:

    ```kotlin
    plugins {
    //  ...
    
        alias(libs.plugins.kotlinx.serialization)
    }
    
    kotlin {
    //  ...
    
        sourceSets {
            androidMain.dependencies {
    //          ...
                implementation(libs.coil.network.android)
            }
            commonMain.dependencies {
    //          ...
                implementation(libs.androidx.navigation.compose)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.napier)
                implementation(libs.coil.compose)
            }
            commonTest.dependencies {
    //          ...
            }
            iosMain.dependencies {
                implementation(libs.ktor.ios)
                implementation(libs.coil.network.ios)
            }
        }
    }
    
    android {
    //    ...
    }
    dependencies {
        debugImplementation(libs.compose.uiTooling)
    }
    ```

<!-- Последний коммит в репозитории Napier был сделан **2 года назад (4 января 2024 года)** -->
<!-- В дальнейшем могут быть проблемы с версиями Kotlin и Napier. Хотелось бы использовать другую библиотеку (например: Kermit) -->

### Рекомпозиция до ViewModel и после его внедрения

#### Приветственный экран

- До внедрения:

![До кодинга Welcome Layout.png](images/До%20кодинга%20Welcome%20Layout.png)

- После внедрения

![После кодинга Welcome Layout.png](images/После%20кодинга%20Welcome%20Layout.png)

#### Экран входа

С этим экраном возникла проблема: из-за viewModel блок из поля ввода перерисовывается больше раз, чем до его внедрения. Решений проблемы не нашел.

Данные картинки отражают состояние, при котором в поле логина введено "data@data.com", а в поле пароля - "1234567890"

- До внедрения

![До кодинга Login Layout.png](images/До%20кодинга%20Login%20Layout.png)

- После внедрения

![До кодинга Login Layout.png](images/После%20кодинга%20Login%20Layout.png)


### Структура проекта

[//]: # (TODO: добавить структуру проекта после его создания)

```bash
nothing here =(
```

### Подробности реализации

[//]: # (TODO: описать структуру проекта ТОЛЬКО ПОСЛЕ ЕГО НАПИСАНИЯ И ТЕСТИРОВАНИЯ)

nothing here =(

### Требования к ОС

|   ОС    | Минмальная версия  | Максимальная версия  |
|:-------:|:------------------:|:--------------------:|
| Android | Android 8.0 (Oreo) | Android 16 (Baklava) |
|   iOS   |         —          |          —           |


Данное приложение протестировано только на Android-устройствах, начиная с Android 8.0 (Oreo) до
Android 16 (Baklava) включительно.

iOS версия приложения пока что находится в разработке и тестировании.

<!-- Activity Lifecycle literally drains the desire to live =(... -->
<!-- ...while iOS development on Windows kills instantly -->

### Тестировавшиеся устройства

1. **Эмуляторы**:
     - Google Pixel 7 (API 36, Android 16)
2. **Реальные устройства**:
    - Xiaomi Redmi 12 (API 33, Android 13 _with MIUI 14_). Идентификатор устройства: Xiaomi 23053RN02A




## Default Description

This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform
  applications.
  It contains several subfolders:
    - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the
      folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
      Similarly, if you want to edit the Desktop (JVM) specific part,
      the [jvmMain](./composeApp/src/jvmMain/kotlin)
      folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose
  Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for
  your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run
widget
in your IDE’s toolbar or build it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run
widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
