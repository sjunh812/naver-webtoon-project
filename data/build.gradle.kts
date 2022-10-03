plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":domain"))
}