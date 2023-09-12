import extensions.getMyLibraryVersion

plugins {
    id("multiplatform-library-convention")

    id("publication-settings")
    id("maven-publish")
}

group = "io.github.dmitriy1892.kmm"
version = getMyLibraryVersion()

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":mvi-core"))

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kmm.mvvm.core)
                api(libs.kmm.utils)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel)
            }
        }
    }
}

android {
    namespace = "io.github.dmitriy1892.kmm.mvi.kmm.mvvm"
}