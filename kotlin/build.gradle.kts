plugins {
    kotlin("jvm") version "2.1.21"
    `maven-publish`
    signing
}

group = "se.idkollen"
version = "0.1.0"

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.9.0")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets["main"].kotlin)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    // Populate with Dokka output when dokka plugin is applied; otherwise ships empty.
    from(tasks.findByName("dokkaJavadoc")?.outputs ?: files())
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin") {
            from(components["kotlin"])
            artifact(sourcesJar)
            artifactId = "idkollen-client-kotlin"
            pom {
                name.set("IDkollen Client Kotlin")
                description.set("Kotlin coroutine extensions for the IDkollen REST API client")
                url.set("https://idkollen.se")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/idkollen/idkollen-client-jvm.git")
                    url.set("https://github.com/idkollen/idkollen-client-jvm")
                }
            }
        }
    }
    repositories {
        maven {
            name = "sonatype"
            url = uri(
                if (version.toString().endsWith("-SNAPSHOT"))
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                else
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            )
            credentials {
                username = providers.gradleProperty("ossrhUsername").orNull
                password = providers.gradleProperty("ossrhPassword").orNull
            }
        }
    }
}

signing {
    val key = providers.gradleProperty("signingKey").orNull
    val pwd = providers.gradleProperty("signingPassword").orNull
    if (key != null && pwd != null) {
        useInMemoryPgpKeys(key, pwd)
        sign(publishing.publications["mavenKotlin"])
    }
}
