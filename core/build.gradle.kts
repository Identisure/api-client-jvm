plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "se.idkollen"
version = "0.1.0"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.jspecify:jspecify:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "idkollen-client-core"
            pom {
                name.set("IDkollen Client Core")
                description.set("Java client for the IDkollen REST API")
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
                if (version.toString().endsWith("-SNAPSHOT")) {
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                } else {
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                }
            )
            credentials {
                username = providers.gradleProperty("ossrhUsername").orNull
                password = providers.gradleProperty("ossrhPassword").orNull
            }
        }
    }
}

tasks.javadoc {
    val opts = options as StandardJavadocDocletOptions
    opts.addBooleanOption("Xdoclint:none", true)
    // Only document exported packages — hide se.idkollen.client.internal.
    opts.addStringOption("-show-packages", "exported")
    opts.addStringOption("-show-module-contents", "api")
}

signing {
    val key = providers.gradleProperty("signingKey").orNull
    val pwd = providers.gradleProperty("signingPassword").orNull

    if (key != null && pwd != null) {
        useInMemoryPgpKeys(key, pwd)
        sign(publishing.publications["mavenJava"])
    }
}
