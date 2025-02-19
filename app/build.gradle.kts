/*
* Copyright (C) GM Global Technology Operations LLC 2024
* All Rights Reserved.
* GM Confidential Restricted.
*/
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

//apply from: '../buildsystem/config.gradle'
//apply from: '../buildsystem/dependencies.gradle'
//apply from: '../buildsystem/jacoco.gradle'
//apply from: '../buildsystem/utilTasks.gradle'

//buildDir = new File(buildDir, project.name)

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

//    project.archivesBaseName = "tmapplugin"
//    project.archivesName.set("tmapplugin")

    defaultConfig {
        applicationId = "com.gm.carcontrolsim"
        versionCode = 1
        versionName = "0.1.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        group = "GM-CarcontrolSim"
        setProperty("archivesBaseName", group as String + "-" + versionName)

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        debug {
            isDebuggable = true

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }


    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        packaging {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    namespace = "com.gm.carcontrolsim"
}

dependencies {

    implementation(project(":data"))

    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.javax.inject)

    // Android
    implementation(libs.androidx.appcompat)
//    implementation lib.constraint_layout
//
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    //implementation(libs.androidx.recyclerview)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
//
//
//    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmoel)
    implementation(libs.androidx.lifecycle.livedata)
//
//    // ktx
    implementation(libs.androidx.activity.ktx)
//    implementation lib.ktx_fragment

    //newsapi 추가
    implementation (libs.kotlinx.coroutines.core.v152)
    implementation (libs.kotlinx.coroutines.android.v152)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //image 추가
    implementation (libs.picasso)

    //instrumented test 추가
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v113)
    androidTestImplementation(libs.androidx.espresso.core.v340)
    testImplementation(libs.mockito.core.v3112)
    androidTestImplementation(libs.mockito.android.v500)
    androidTestImplementation(libs.androidx.core.testing.v210)

}
