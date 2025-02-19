/*
* Copyright (C) GM Global Technology Operations LLC 2024
* All Rights Reserved.
* GM Confidential Restricted.
*/
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
//    alias(libs.plugins.protobuf) apply false
}

tasks.create<Delete>("clean") {
    group = "build"
    delete(
        fileTree(rootProject.buildDir)
    )
}
