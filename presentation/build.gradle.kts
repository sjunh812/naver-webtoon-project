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
    implementation(project(":data"))

    implementation(Library.AndroidX.CORE)
    implementation(Library.AndroidX.APPCOMPAT)
    implementation(Library.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Library.AndroidX.NAVIGATION_RUNTIME)
    implementation(Library.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Library.AndroidX.NAVIGATION_UI)
    implementation(Library.AndroidX.PAGING)
    implementation(Library.AndroidX.SPLASH_SCREEN)
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    androidTestImplementation(Library.AndroidX.ANDROID_JUNIT)
    androidTestImplementation(Library.AndroidX.ESPRESSO)

    implementation(Library.Google.MATERIAL)

    implementation(Library.Junit.JUNIT)

    implementation(Library.Hilt.ANDROID)
    kapt(Library.Hilt.ANDROID_COMPILER)

    implementation(Library.Glide.GLIDE)
    kapt(Library.Glide.GLIDE_COMPILER)

    implementation(Library.DateTime.JODA_TIME)
}