object Version {

    const val COROUTINE = "1.6.1"

    const val CORE = "1.8.0"
    const val APPCOMPAT = "1.4.2"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val NAVIGATION = "2.5.2"
    const val PAGING = "3.1.1"
    const val SPLASH_SCREEN = "1.0.0"
    const val ANDROID_JUNIT = "1.1.3"
    const val ESPRESSO = "3.4.0"

    const val MATERIAL = "1.8.0-alpha02"

    const val JUNIT = "4.13.2"

    const val HILT = "2.42"

    const val RETROFIT = "2.9.0"
    const val MOSHI = "1.9.3"
    const val OKHTTP = "5.0.0-alpha.7"
    const val JSOUP = "1.15.3"

    const val GLIDE = "4.13.2"
    const val GLIDE_COMPILER = "4.12.0"

    const val JODA_TIME = "2.12.0"
}

object Library {

    object Kotlin {
        const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINE}"
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINE}"
    }

    object AndroidX {

        const val CORE = "androidx.core:core-ktx:${Version.CORE}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.APPCOMPAT}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
        const val NAVIGATION_RUNTIME = "androidx.navigation:navigation-runtime-ktx:${Version.NAVIGATION}"
        const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Version.NAVIGATION}"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Version.NAVIGATION}"
        const val NAVIGATION_SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.NAVIGATION}"
        const val PAGING = "androidx.paging:paging-runtime-ktx:${Version.PAGING}"
        const val PAGING_COMMON = "androidx.paging:paging-common-ktx:${Version.PAGING}"
        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${Version.SPLASH_SCREEN}"
        const val ANDROID_JUNIT = "androidx.test.ext:junit:${Version.ANDROID_JUNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    }

    object Google {

        const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    }

    object Junit {

        const val JUNIT = "junit:junit:${Version.JUNIT}"
    }

    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Version.HILT}"
        const val CORE = "com.google.dagger:hilt-core:${Version.HILT}"
        const val ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
        const val COMPILER = "com.google.dagger:hilt-compiler:${Version.HILT}"
    }

    object Network {

        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${Version.RETROFIT}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Version.MOSHI}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Version.OKHTTP}"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Version.OKHTTP}"
        const val JSOUP = "org.jsoup:jsoup:${Version.JSOUP}"
    }

    object Glide {

        const val GLIDE = "com.github.bumptech.glide:glide:${Version.GLIDE}"
        const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Version.GLIDE_COMPILER}"
    }

    object DateTime {

        const val JODA_TIME = "joda-time:joda-time:${Version.JODA_TIME}"
    }
}