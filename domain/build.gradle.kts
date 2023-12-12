plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}

dependencies {
    implementation(Libs.Gson.library)
    implementation(Libs.Kotlin.coroutinesCore)
    implementation(project(mapOf("path" to ":utils:utils")))
}