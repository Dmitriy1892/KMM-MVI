import extensions.getApplicationVersionCode
import extensions.getApplicationVersionName

plugins {
    id("android-application-convention")
    id("android-base-compose-convention")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(project(":sample"))
            implementation(project(":mvi-core"))
            implementation(project(":mvi-android-mvvm"))
            implementation(project(":mvi-kmm-mvvm"))

            implementation(libs.bundles.androidApp)
            implementation(libs.kmm.mvvm.core)
        }
    }
}

android {
    namespace = "io.github.dmitriy1892.app"

    defaultConfig {
        applicationId = "io.github.dmitriy1892.app"

        versionCode = getApplicationVersionCode()
        versionName = getApplicationVersionName()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}