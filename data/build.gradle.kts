plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":domain"))

    implementation(Library.Kotlin.COROUTINE_CORE)

    implementation(Library.AndroidX.PAGING_COMMON)

    implementation(Library.Network.RETROFIT)
    implementation(Library.Network.CONVERTER_MOSHI)
    implementation(Library.Network.MOSHI)
    implementation(Library.Network.OKHTTP)
    implementation(Library.Network.LOGGING_INTERCEPTOR)
    implementation(Library.Network.JSOUP)

    implementation(Library.Hilt.CORE)
    kapt(Library.Hilt.COMPILER)
}