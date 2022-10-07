plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Library.AndroidX.CORE)
    implementation(Library.AndroidX.APPCOMPAT)
    implementation(Library.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Library.AndroidX.NAVIGATION_RUNTIME)
    implementation(Library.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Library.AndroidX.ANDROID_JUNIT)
    implementation(Library.AndroidX.ESPRESSO)

    implementation(Library.Google.MATERIAL)

    implementation(Library.Junit.JUNIT)

    implementation(Library.Hilt.ANDROID)
    kapt(Library.Hilt.ANDROID_COMPILER)
}