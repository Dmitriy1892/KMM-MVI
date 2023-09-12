import extensions.iosRegularFramework

plugins {
    id("multiplatform-compose-setup")
}

kotlin {
    iosRegularFramework {
        baseName = "sample"
        transitiveExport = false
        isStatic = true

        export(project(":mvi-core"))
        export(project(":mvi-kmm-mvvm"))

        export(libs.kmm.utils)
        export(libs.kmm.mvvm.core)
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":mvi-core"))
                implementation(project(":mvi-kmm-mvvm"))

                implementation(libs.kmm.utils)
                implementation(libs.kmm.mvvm.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel)
            }
        }

        iosMain {
            dependencies {
                api(project(":mvi-core"))
                api(project(":mvi-kmm-mvvm"))

                api(libs.kmm.utils)
                api(libs.kmm.mvvm.core)
            }
        }
    }
}

android {
    namespace = "io.github.dmitriy1892.kmm.mvi.sample"
}