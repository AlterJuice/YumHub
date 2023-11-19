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
    namespace = "com.alterjuice.network"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

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
            yumHubProps.getFieldValueOrThrow(YumHubProperties.RELEASE_GOOGLE_KEY) { _, value ->
                buildConfigField("String", "GOOGLE_KEY", value.toString())
            }
        }

        debug {
            yumHubProps.getFieldValueOrThrow(YumHubProperties.DEBUG_GOOGLE_KEY) { _, value ->
                buildConfigField("String", "GOOGLE_KEY", value.toString())
            }
        }

        all {
            yumHubProps.getFieldValueOrThrow(YumHubProperties.PIXABAY_API_KEY) { keyName, value ->
                buildConfigField("String", keyName, value.toString())
            }
            yumHubProps.getFieldValueOrThrow(YumHubProperties.OPEN_AI_API_KEY) { keyName, value ->
                buildConfigField("String", keyName, value.toString())
            }
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmVersion
    }
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.material)

    implementation(platform(Libs.Network.OkHTTP.okHttpBom))
    implementation(Libs.Network.OkHTTP.interceptor)
    implementation(Libs.Network.OkHTTP.retrofit)
    implementation(Libs.Network.OkHTTP.retrofitConverter)
    implementation(Libs.Gson.retrofitConverter)

    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}