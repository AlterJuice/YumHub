plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.alterjuice.yumhub"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "com.alterjuice.yumhub"
        minSdk = Config.minSdk
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
        }
    }
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.material)

    val platform = platform(Libs.AndroidX.Compose.Bom.composeBOM)
    implementation(platform)
    debugImplementation(platform)
    implementation(Libs.AndroidX.Compose.Bom.composeUI)
    debugImplementation(Libs.AndroidX.Compose.Bom.composeUiTooling)
    implementation(Libs.AndroidX.Compose.Bom.composeUiToolingPreview)
    implementation(Libs.AndroidX.Compose.activityCompose)
    implementation(Libs.AndroidX.Compose.Bom.composeRuntime)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Compose.Accompanist.insets)
    implementation(Libs.AndroidX.Compose.Accompanist.insetsUI)
    implementation(Libs.AndroidX.Compose.Accompanist.pager)
    implementation(Libs.AndroidX.Compose.Accompanist.pagerIndicator)
    implementation(Libs.AndroidX.Compose.constraintLayout)
    implementation("androidx.compose.material3:material3:1.2.0-alpha08") // Stable: 1.1.1

    implementation("io.coil-kt:coil-compose:2.4.0")

    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junitExt)
    androidTestImplementation(Libs.Testing.espressoCore)
}