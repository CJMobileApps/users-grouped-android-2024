plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.cjmobileapps.usersgroupedandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cjmobileapps.usersgroupedandroid"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("dev") {
            buildConfigField("String", "BASE_URL", "\"https://fetch-hiring.s3.amazonaws.com/\"")
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Android Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(libs.navigation.compose)
    implementation(libs.coil.kt.coil.compose)

    // Android Compose Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Hilt
    implementation(libs.google.hilt.android)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.test.rules)
    ksp(libs.google.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit 2
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.squareup.retrofit2.converter.gson)

    // Coroutines
    implementation(libs.jakewharton.retrofit.kotlin.coroutines.adapter)
    testImplementation(libs.kotlinx.coroutines.test)

    // Junit 5
    testImplementation(libs.junit5)
    testImplementation(libs.mockitoKotlin)

    // Junit 4
    testImplementation(libs.junit4)
    testRuntimeOnly(libs.junit4.vintage.engine)

    // Timber logger
    implementation(libs.timber)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}
