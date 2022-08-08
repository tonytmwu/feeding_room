import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.File
import java.io.FileInputStream
import java.util.*

val keyProperty = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "feeding_room_key.properties")))
}
val localProperty = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdkVersion = Config.compileSdk

    defaultConfig {
        applicationId = "com.net.feedingroom"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures.viewBinding = true

    signingConfigs {

        named("debug") {
            keyAlias = localProperty.getProperty("keyAlias")
            keyPassword = localProperty.getProperty("keyPassword")
            storeFile = file(rootProject.file(localProperty.getProperty("storeFile")))
            storePassword = localProperty.getProperty("storePassword")
        }

        register("release") {
            keyAlias = keyProperty.getProperty("keyAlias")
            keyPassword = keyProperty.getProperty("keyPassword")
            storeFile = file(keyProperty.getProperty("storeFile"))
            storePassword = keyProperty.getProperty("storePassword")
        }
    }

    buildTypes {
        named("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        named("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation(fileTree( mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs") ))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:26.0.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.appcompat:appcompat:1.4.2")
    kapt("androidx.room:room-compiler:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    // google map
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:20.0.0")
    // fragment ktx
    implementation("androidx.fragment:fragment-ktx:1.5.1")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Coil
    implementation("io.coil-kt:coil:1.4.0")
    // shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("androidx.activity:activity-ktx:1.5.1")
    implementation("androidx.fragment:fragment-ktx:1.5.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}