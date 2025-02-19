/*
* Copyright (C) GM Global Technology Operations LLC 2024
* All Rights Reserved.
* GM Confidential Restricted.
*/

pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CarControlSim"

include(":app")
include(":data")
include(":domain")
