import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val yumHubProps = Properties()
val yumHubPropsFile = rootProject.file(YumHubProperties.filePath)
checkPropertyFileExistsOrThrow(yumHubPropsFile) { file ->
    yumHubProps.load(FileInputStream(file))
}

android {
    namespace = "com.alterjuice.chat_assistant"
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

        all {
            yumHubProps.getFieldValueOrThrow(YumHubProperties.OPEN_AI_API_KEY) { keyName, value ->
                buildConfigField("String", keyName, value.toString())
            }
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
    implementation(Libs.AndroidX.material)

    val platform = platform(Libs.AndroidX.Compose.Bom.composeBOM)
    implementation(platform)
    debugImplementation(platform)
    implementation(Libs.AndroidX.Compose.Bom.composeUI)
    implementation(Libs.AndroidX.Compose.Bom.composeMaterial3)
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

    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)


    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junitExt)
    androidTestImplementation(Libs.Testing.espressoCore)

    implementation(project(":utils:compose_utils"))

    implementation("com.aallam.openai:openai-client:3.0.0")
    implementation("io.ktor:ktor-client-android:2.2.4")
}