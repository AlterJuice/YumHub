object Libs {
    object Kotlin {
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    }

    object Adjust {
        const val adjustAndroid = "com.adjust.sdk:adjust-android:${Versions.adjustAndroid}"
    }

    object Gradle {
        const val androidGradle = "com.android.tools.build:gradle:${Versions.gradle}"
    }
    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    }

    object AndroidX {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

        object Compose {
            object Bom {
                const val composeBOM = "androidx.compose:compose-bom:${Versions.composeBOM}"
                const val composeRuntime = "androidx.compose.runtime:runtime"
                const val composeUI = "androidx.compose.ui:ui"
                const val composeAnimation = "androidx.compose.animation:animation"
                const val composeMaterial = "androidx.compose.material:material"
                const val composeMaterial3 = "androidx.compose.material3:material3"
                const val composeGraphics = "androidx.compose.ui:ui-graphics"
                const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
                const val composeUiTooling = "androidx.compose.ui:ui-tooling"
                const val testJunit4 = "androidx.compose.ui:ui-test-junit4"
                const val foundation = "androidx.compose.foundation:foundation"
            }

            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutCompose}"

            const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
            const val navigation =
                "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
            const val activityCompose =
                "androidx.activity:activity-compose:${Versions.activityCompose}"

            const val customView = "androidx.customview:customview:${Versions.customView}"
            const val customViewPoolingContainer =
                "androidx.customview:customview-poolingcontainer:${Versions.customViewPoolingContainer}"

            object Accompanist {
                const val pager =
                    "com.google.accompanist:accompanist-pager:${Versions.pagerAccompanist}"
                const val pagerIndicator =
                    "com.google.accompanist:accompanist-pager-indicators:${Versions.pagerIndicatorsAccompanist}"
                const val systemUiController =
                    "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanistSystemUiController}"
                const val insets =
                    "com.google.accompanist:accompanist-insets:${Versions.accompanistSystemUiController}"
                const val insetsUI =
                    "com.google.accompanist:accompanist-insets-ui:${Versions.accompanistSystemUiController}"
                const val navigationMaterial =
                    "com.google.accompanist:accompanist-navigation-material:${Versions.navigationMaterial}"
                const val webview =
                    "com.google.accompanist:accompanist-webview:${Versions.accompanistWebView}"
                const val swipeToRefresh =
                    "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeToRefresh}"
                const val accompanistPermissions =
                    "com.google.accompanist:accompanist-permissions:${Versions.permissions}"
            }

            const val markdown = "com.github.jeziellago:compose-markdown:${Versions.markdown}"
        }

        object Livecycle {
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"
            const val liveDataKtx =
                "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataKtx}"
            const val viewModelKtx =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.room}"
            const val testing = "androidx.room:room-testing:${Versions.room}"
        }

        object Security {
            const val crypto = "androidx.security:security-crypto:${Versions.crypto}"
            const val identityCredential =
                "androidx.security:security-identity-credential:${Versions.identityCredential}"
            const val appAuthenticator =
                "androidx.security:security-app-authenticator:${Versions.appAuthenticator}"
        }

        const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
        const val testRules = "androidx.test:rules:${Versions.testRules}"
    }

    object Log {
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Dagger {
        const val library = "com.google.dagger:dagger-android:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.daggerCompiler}"
    }

    object GooglePlayServices {
        const val ads = "com.google.android.gms:play-services-ads:${Versions.googleAds}"
        const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
        const val fitness = "com.google.android.gms:play-services-fitness:${Versions.googleFitness}"
        const val auth = "com.google.android.gms:play-services-auth:${Versions.googleAuth}"
    }

    object Firebase {
        const val crashlyticsGradle =
            "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsGradle}"
        const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val storage = "com.google.firebase:firebase-storage-ktx"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val dynamicLinks = "com.google.firebase:firebase-dynamic-links-ktx"
        const val config = "com.google.firebase:firebase-config-ktx"
        const val authUI = "com.firebaseui:firebase-ui-auth:${Versions.firebaseAuthUI}"
    }

    object Testing {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
        const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
        const val junit = "junit:junit:${Versions.junit}"
        const val truth = "com.google.truth:truth:${Versions.junitExt}"
        const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoAndroid}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    }

    object Network {
        object Apollo {
            const val runtime = "com.apollographql.apollo:apollo-runtime:${Versions.apollo}"
            const val normalizedCache =
                "com.apollographql.apollo:apollo-normalized-cache-sqlite:${Versions.apollo}"
            const val coroutinesSupport =
                "com.apollographql.apollo:apollo-coroutines-support:${Versions.apollo}"
            const val androidSupport =
                "com.apollographql.apollo:apollo-android-support:${Versions.apollo}"
            const val gradlePlugin =
                "com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}"
        }

        object OkHTTP {
            const val okHttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okHttp}"
            const val library = "com.squareup.okhttp3:okhttp"
            const val interceptor = "com.squareup.okhttp3:logging-interceptor"
            const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
            const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
            const val retrofitConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofitConverter}"
        }

        object Moshi {
            const val library = "com.squareup.moshi:moshi:${Versions.moshi}"
            const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
            const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshiAdapters}"
        }
    }

    object Facebook {
        const val login = "com.facebook.android:facebook-login:${Versions.facebookLogin}"
    }

    object OneSignal {
        const val gradlePlugin =
            "gradle.plugin.com.onesignal:onesignal-gradle-plugin:${Versions.oneSignalPlugin}"
        const val service =
            "com.onesignal:OneSignal:${Versions.oneSignalLibrary}"
    }

    object Gson {
        const val library = "com.google.code.gson:gson:${Versions.gson}"
        const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.gson}"
    }

    object Markdown
}
