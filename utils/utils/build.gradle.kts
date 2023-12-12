plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}
dependencies {
    implementation(Libs.Kotlin.coroutinesCore)
    implementation(Libs.Koin.core)
}