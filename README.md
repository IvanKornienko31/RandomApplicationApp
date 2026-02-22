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
- Napier - кроссплатформенное логирование

<!-- Последний коммит в репозитории Napier был сделан **3 года назад (4 января 2024 года)** -->
<!-- В дальнейшем могут быть проблемы с версиями Kotlin и Napier. Хотелось бы использовать другую библиотеку (например: Kermit) -->

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
    - Xiaomi Redmi 12 (API 33, Android 13 _with MIUI 14_)




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