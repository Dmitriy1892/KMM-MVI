pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KMM-MVI"

includeBuild("build-logic")

include(":androidApp")

include(":mvi-core")
include(":mvi-kmm-mvvm")
include(":mvi-android-mvvm")