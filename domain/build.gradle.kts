/*
* Copyright (C) GM Global Technology Operations LLC 2024
* All Rights Reserved.
* GM Confidential Restricted.
*/

plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.javax.inject)

    testImplementation(libs.test.junit)
    testImplementation(libs.truth)
}
