import java.io.FileInputStream
import java.util.Properties


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val yumHubProps = Properties()
val yumHubPropsFile = rootProject.file(YumHubProperties.filePath)
checkPropertyFileExistsOrThrow(yumHubPropsFile) { file ->
    yumHubProps.load(FileInputStream(file))
}


android {
    namespace = "com.alterjuice.yumhub"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "com.alterjuice.yumhub"
        minSdk = Config.minSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmVersion
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/androidx.compose.*.version"
        }
    }
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    // implementation(Libs.AndroidX.material)
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":core:navigation")))
    implementation(project(mapOf("path" to ":core:repository")))
    implementation(project(mapOf("path" to ":core:database")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":utils:android_utils")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":feature:onboarding")))

    val platform = platform(Libs.AndroidX.Compose.Bom.composeBOM)
    implementation(platform)
    debugImplementation(platform)
    debugImplementation(Libs.AndroidX.Compose.Bom.composeUiTooling)
    implementation(Libs.AndroidX.Compose.Bom.composeUI)
    implementation(Libs.AndroidX.Compose.Bom.composeMaterial3)
    implementation(Libs.AndroidX.Compose.Bom.composeUiToolingPreview)
    implementation(Libs.AndroidX.Compose.activityCompose)
    implementation(Libs.AndroidX.Compose.Bom.composeRuntime)
    implementation(Libs.AndroidX.Compose.navigation)
    // implementation(Libs.AndroidX.Compose.Accompanist.insets)
    // implementation(Libs.AndroidX.Compose.Accompanist.insetsUI)
    // implementation(Libs.AndroidX.Compose.Accompanist.pager)
    // implementation(Libs.AndroidX.Compose.Accompanist.pagerIndicator)
    implementation(Libs.AndroidX.Compose.constraintLayout)
    // implementation("androidx.compose.material3:material3:1.2.0-alpha08") // Stable: 1.1.1

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(project(":feature:chat_assistant"))
    implementation(project(":theming"))
    implementation(project(":feature:meals"))
    implementation(project(":feature:user_profile"))
    implementation(project(":feature:dashboard"))
    implementation(project(":utils:compose_utils"))

    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)


    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junitExt)
    androidTestImplementation(Libs.Testing.espressoCore)
}