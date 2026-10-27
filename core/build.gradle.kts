plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "com.identisure"
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

    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("identisure.fixtures", rootDir.resolve("../fixtures").absolutePath)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "identisure-client-core"
            pom {
                name.set("Identisure Client Core")
                description.set("Java client for the Identisure REST API")
                url.set("https://identisure.com")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/identisure/identisure-client-jvm.git")
                    url.set("https://github.com/identisure/identisure-client-jvm")
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
    // Only document exported packages — hide com.identisure.client.internal.
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
