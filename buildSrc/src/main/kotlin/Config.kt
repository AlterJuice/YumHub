import org.gradle.api.JavaVersion

object Config {
    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmVersion = "17"
    val javaVersion = JavaVersion.VERSION_17
}