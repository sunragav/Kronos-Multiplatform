# Kronos Multiplatform Library
A fork of the [original kronos library](https://github.com/softartdev/Kronos-Multiplatform) with the following changes:
- using cloudflare's time server "time.cloudflare.com" 
- using kotlinx.datetime instead of kotlin.time.
- using com.github.instacart:truetime-android for the android side implementation.


![Maven Central](https://img.shields.io/maven-central/v/io.github.sunragav.kronos/kronos)

Kotlin Multiplatform library for network time synchronization. It extends the [`kotlinx.datetime`](https://kotlinlang.org/api/kotlinx-datetime) API and supports the following platforms:
- Android
- iOS
- Desktop JVM (MacOS, Linux, Windows)
## Usage
### `kotlinx.datetime` Extension
The library [extends the main `Clock` interface](https://github.com/sunragav/Kronos-Multiplatform/blob/main/kronos/src/commonMain/kotlin/com/softartdev/kronos/ClockExt.kt) from the standard library. You can use the [`Clock.Network`](https://github.com/sunragav/Kronos-Multiplatform/blob/main/kronos/src/commonMain/kotlin/com/softartdev/kronos/NetworkClock.kt) class to retrieve the current network time, similar to using the built-in [`Clock.System`](https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/time/Clock.kt#L60) instance.
```kotlin
val networkTime: Instant = Clock.Network.now()  // 2025-06-30T13:42:43.712Z
val systemTime: Instant = Clock.System.now()    // 2025-06-30T13:42:43.566455Z
val diff: Duration = networkTime - systemTime   // 145.545ms
```
### Synchronization
When running the application, it's necessary to synchronize the time with the network using the platform-specific code:
- Android:
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Clock.Network.sync(applicationContext)
    }
}
```
- iOS:
```swift
@main
struct iosApp: App {
    init() {
        Clock.Network.sync()
    }
    var body: some Scene { ... }
}
```
- Desktop JVM:
```kotlin
fun main() {
    Clock.Network.sync()
    ...
}
```
### Installation
The latest release is available on [Maven Central](https://repo1.maven.org/maven2/io/github/softartdev/kronos/).
1. Add the Maven Central repository if it is not already included:
```kotlin
repositories {
    mavenCentral()
}
```
2. In multiplatform projects, add the following dependency to the `commonMain` source set dependencies:
```kotlin
commonMain {
    dependencies {
        implementation("io.github.sunragav:kronos:$latestVersion")
    }
}
```
## Implementation
The main common interface is implemented using the following:
- [com.github.instacart/truetime-android](https://github.com/instacart/truetime-android) for Java & Android
- [MobileNativeFoundation/Kronos](https://github.com/MobileNativeFoundation/Kronos) for iOS

The project is built and tested using the following:
- [Swift Klib Gradle Plugin](https://github.com/ttypic/swift-klib-plugin) for including Swift source files in KMM shared module
- [Compose Multiplatform, by JetBrains](https://github.com/JetBrains/compose-jb) for UI samples
