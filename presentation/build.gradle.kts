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
}

dependencies {
    implementation(project(":domain"))

    implementation(Library.AndroidX.CORE)
    implementation(Library.AndroidX.APPCOMPAT)
    implementation(Library.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Library.AndroidX.ANDROID_JUNIT)
    implementation(Library.AndroidX.ESPRESSO)

    implementation(Library.Google.MATERIAL)

    implementation(Library.Junit.JUNIT)

    implementation(Library.Hilt.ANDROID)
    kapt(Library.Hilt.ANDROID_COMPILER)
}

//plugins {
//    id("com.android.application")
//}
//
//android {
//    namespace 'org.sjhstudio.naverwebtoon'
//    compileSdk 32
//
//    defaultConfig {
//        applicationId "org.sjhstudio.naverwebtoon"
//        minSdk 24
//        targetSdk 32
//        versionCode 1
//        versionName "1.0"
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = '1.8'
//    }
//}

//dependencies {
//
//    implementation 'androidx.core:core-ktx:1.7.0'
//    implementation 'androidx.appcompat:appcompat:1.5.1'
//    implementation 'com.google.android.material:material:1.6.1'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//}