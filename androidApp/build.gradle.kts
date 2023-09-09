plugins {
    id("android-application-convention")
    id("android-base-compose-convention")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":mvi-core"))
                implementation(libs.koin.core)
            }
        }
        
        androidMain {
            dependencies {
                implementation(project(":mvi-android-mvvm"))
                implementation(libs.bundles.android.feature.compose)
                implementation(libs.kmm.utils)
            }
        }
    }
}

android {
    namespace = "io.github.dmitriy1892.app"

    defaultConfig {
        applicationId = "io.github.dmitriy1892.app"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}