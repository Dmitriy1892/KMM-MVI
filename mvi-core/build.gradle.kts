import extensions.autoIncrementBuildVersionNumber
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
                implementation(libs.kotlinx.coroutines.core)
                api(libs.kmm.utils)
            }
        }
    }
}

android {
    namespace = "io.github.dmitriy1892.kmm.mvi.core"
}

tasks.register("autoIncrementBuild") {
    group = "publishing"
    description = "Publishes all Maven publications to the external Maven repository."

    doFirst {
        autoIncrementBuildVersionNumber()
    }
}