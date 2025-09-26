plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.swift.klib)
    alias(libs.plugins.vanniktech.maven.publish)
}
group = "io.github.sunragav.kronos"
version = libs.versions.kronos.get()

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
    jvm()
    androidTarget {
        publishLibraryVariants("release", "debug")
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.compilations {
            val main by getting {
                cinterops {
                    create("KronosMultiplatform")
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val jvmMain by getting {
            dependencies {
                api(libs.lyft.kronos.java)
            }
        }
        val jvmTest by getting
        val androidMain by getting {
            dependencies {
                api(libs.truetime.android)
                api(libs.kotlinx.coroutines.core)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.androidx.test)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.softartdev.kronos"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
    }
}

swiftklib {
    create("KronosMultiplatform") {
        path = file("native/Kronos")
        packageName("com.softartdev.kronos")
    }
}

mavenPublishing {
    group = "io.github.sunragav.kronos"
    version = libs.versions.kronos.get()
    pom {
        name.set("Kronos Multiplatform")
        description.set("A Kotlin Multiplatform library for time synchronization.")
        inceptionYear.set("2023")
        url.set("https://github.com/sunragav/Kronos-Multiplatform/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("sunragav")
                name.set("Sundara Raghavan Santhanam")
                url.set("https://github.com/sunragav/")
                email.set("sunragav@gmail.com")
            }
            developer {
                id.set("softartdev")
                url.set("https://github.com/softartdev/")
                name.set("Artur Babichev")
                email.set("artik222012@gmail.com")
            }
        }
        scm {
            url.set("https://github.com/sunragav/Kronos-Multiplatform/")
            connection.set("scm:git:git://github.com/sunragav/Kronos-Multiplatform.git")
            developerConnection.set("scm:git:ssh://git@github.com/sunragav/Kronos-Multiplatform.git")
        }
    }
    publishToMavenCentral()
    signAllPublications()
}
