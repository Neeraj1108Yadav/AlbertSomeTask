import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.dagger.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.albertsome_task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.albertsome_task"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation(libs.com.square)
    implementation(libs.com.gson)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.logging.interceptor)
    implementation(libs.squareup.okhttp.mockwebserver)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Coroutine
    implementation(libs.androidx.coroutine)

    //Architectural Components
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.livedata)
    implementation(libs.androidx.lifecycle)

    //Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigation.ui)

    //Coil
    implementation(libs.io.coil.kt.coil3)
    implementation(libs.io.coil.kt.coil3.network)

}

kapt {
    correctErrorTypes = true
}