plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("com.github.com.johnrengelman.shadow") version "0.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}