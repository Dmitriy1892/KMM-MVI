plugins {
    alias(libs.plugins.moko.resources).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory.asFile.get())
}