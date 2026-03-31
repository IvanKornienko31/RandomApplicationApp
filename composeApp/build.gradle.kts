import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.riflesso)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.stability.analyzer)
    alias(libs.plugins.build.konfig)
}

val localPropertiesFile: File = rootProject.file("local.properties")
val localProperties = Properties().apply {
    load(localPropertiesFile.inputStream())
}

buildkonfig {
    packageName = "com.github.ivankornienko31"
    objectName = "StepikClientApp"

    val clientId = localProperties["stepik.client.id"] as String
    val clientSecret = localProperties["stepik.client.secret"] as String

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING,"STEPIK_CLIENT_ID", clientId)
        buildConfigField(FieldSpec.Type.STRING,"STEPIK_CLIENT_SECRET", clientSecret)
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.riflesso)
            implementation(libs.ktor.client.android)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.napier)
            implementation(libs.coil.compose)
            implementation(libs.material.icons.core)      // Remove before update
            implementation(libs.ktor.client.engine)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.coil.network.ktor)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.okio)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}
android {
    namespace = "com.github.ivankornienko31.stepikclientapplication"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.github.ivankornienko31.stepikclientapplication"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}


dependencies {
    debugImplementation(libs.compose.uiTooling)
}

