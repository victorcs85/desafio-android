plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.picpay.desafio.android"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.picpay.desafio.android"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
          //  isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "**/MANIFEST.MF"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        animationsDisabled = true
        unitTests.all {
            it.reports.html.required.set(true)
        }
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    //region App
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.multidex)
    implementation(libs.timber)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.okhttp)
    debugImplementation(libs.mockwebserver)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)

    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.kotlin.reflect)
    implementation(libs.coil.compose)
    ksp(libs.moshi.kotlin.codegen)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //endregion
    //region Unit Tests
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk) { exclude(module = "org.objenesis") }
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.rules)
    testImplementation(libs.androidx.runner)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.koin.android.test)
    //endregion
    //region Instrumented Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.android)
    androidTestImplementation(libs.koin.androidx.compose)
    androidTestImplementation(libs.androidx.espresso.contrib) {
        exclude(module = "protobuf-lite")
    }
    androidTestImplementation(libs.androidx.fragment.testing)
    androidTestImplementation(libs.mockk.android) { exclude(module = "org.objenesis") }
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.kotlinx.coroutines.test) {
        exclude(module = "kotlinx-coroutines-debug")
    }
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hamcrest.library)
    androidTestImplementation(libs.hamcrest) {
        exclude(group = "junit")
    }
    //endregion
}
