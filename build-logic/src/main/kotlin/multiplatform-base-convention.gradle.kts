import extensions.projectJavaVersion

plugins {
    kotlin("multiplatform")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = projectJavaVersion.toString()
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}