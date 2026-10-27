plugins {
    kotlin("jvm")
    id("com.diffplug.spotless")
    id("com.vanniktech.maven.publish")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        allWarningsAsErrors = true
    }
}

spotless {
    kotlin {
        ktfmt("0.64").kotlinlangStyle()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.9.0")
}
