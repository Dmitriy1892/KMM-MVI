import extensions.getMyLibraryVersion

plugins {
    id("multiplatform-compose-setup")

    id("publication-settings")
    id("maven-publish")
}

group = "io.github.dmitriy1892.kmm"
version = getMyLibraryVersion()

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":mvi-core"))
            api(libs.decompose.essenty.lifecycle)
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.feature.compose)
        }
    }
}

android {
    namespace = "io.github.dmitriy1892.kmm.mvi.compose.multiplatform"
}