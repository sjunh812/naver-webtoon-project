// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Library.AndroidX.NAVIGATION_SAFE_ARGS)
    }
}

plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
