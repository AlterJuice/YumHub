plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.alterjuice.onboarding"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    kotlinOptions {
        jvmTarget = Config.jvmVersion
    }
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(project(mapOf("path" to ":utils:android_utils")))
    implementation(project(mapOf("path" to ":utils:compose_utils")))
    implementation(project(mapOf("path" to ":utils:utils")))
    implementation(project(mapOf("path" to ":theming")))
    implementation(project(mapOf("path" to ":resources")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":core:navigation")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":core:repository")))

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

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(project(":theming"))
    implementation(project(":utils:compose_utils"))

    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)
    implementation(Libs.Gson.library)

    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junitExt)
    androidTestImplementation(Libs.Testing.espressoCore)
}