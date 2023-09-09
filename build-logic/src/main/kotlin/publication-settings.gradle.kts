import extensions.getSecretProperties
import extensions.getExtraString

plugins {
    `maven-publish`
    signing
}

// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

val secretPropsFile = getSecretProperties()

secretPropsFile.onEach { (name, value) ->
    ext[name.toString()] = value
}

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    publications.withType<MavenPublication> {
        pom {
            name.set("KMM-MVI")
            description.set("KMM-MVI is a library with MVI classes and extensions for Kotlin Multiplatform Mobile development")
            url.set("https://github.com/Dmitriy1892/KMM-MVI")

            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://github.com/Dmitriy1892/KMM-MVI/LICENSE.md")
                }
            }

            developers {
                developer {
                    id.set("kodim1892")
                    name.set("Dmitriy Kotikov")
                    email.set("kodim1892@gmail.com")
                }
            }

            scm {
                url.set("https://github.com/Dmitriy1892/KMM-MVI")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}