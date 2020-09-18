plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        applicationId = "com.net.feedingroom"
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures.viewBinding = true

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(fileTree( mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs") ))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.navigation:navigation-fragment:2.3.0")
    implementation("androidx.navigation:navigation-ui:2.3.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.8.6")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}