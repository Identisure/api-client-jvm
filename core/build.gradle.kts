plugins {
    `java-library`
    id("com.diffplug.spotless")
    id("com.vanniktech.maven.publish")
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.jspecify:jspecify:1.0.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")

    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

spotless {
    java {
        palantirJavaFormat("2.102.0")
    }
}

tasks.compileJava {
    // OkHttp's class files carry Kotlin annotations; without kotlin.stdlib in the module graph javac warns on every use.
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Werror", "--add-modules", "kotlin.stdlib"))
}

tasks.compileTestJava {
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Werror"))
}

tasks.test {
    useJUnitPlatform()
    systemProperty("identisure.fixtures", rootDir.resolve("fixtures").absolutePath)
}

tasks.javadoc {
    // javadoc documents every source file it is given, ignoring --show-packages, so hide the internal
    // package by excluding it and let javadoc resolve its types from the source path instead.
    exclude("com/identisure/client/internal/**")
    val opts = options as StandardJavadocDocletOptions
    opts.addStringOption("-source-path", file("src/main/java").path)
    opts.addBooleanOption("Xdoclint:none", true)
    opts.addStringOption("-show-module-contents", "api")
}
