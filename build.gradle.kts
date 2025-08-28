plugins {
    kotlin("jvm") version "2.1.21"
    application
}

group = "site.thatkid"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.13.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("site.thatkid.MainKt")
}