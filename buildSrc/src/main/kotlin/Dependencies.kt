object Version {

    const val CORE = "1.8.0"
    const val APPCOMPAT = "1.4.2"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val ANDROID_JUNIT = "1.1.3"
    const val ESPRESSO = "3.4.0"

    const val MATERIAL = "1.6.1"

    const val JUNIT = "4.13.2"

    const val HILT = "2.42"
}

object Library {

    object AndroidX {

        const val CORE = "androidx.core:core-ktx:${Version.CORE}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.APPCOMPAT}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
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
}