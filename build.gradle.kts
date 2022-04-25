import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("fabric-loom") version "0.10-SNAPSHOT"
}

val minecraftVersion = "1.18.2"
val kotlinVersion = "1.6.20"

repositories {
    maven {
        url = uri("https://maven.meteordev.org/releases")
    }
    maven {
        url = uri("https://maven.meteordev.org/snapshots")
    }
    maven {
        url = uri("https://jitpack.io")
    }

    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.1:v2")

    modImplementation("net.fabricmc:fabric-loader:0.13.3")
    modImplementation("meteordevelopment:meteor-client:0.4.7")

    implementation("com.github.therealbush:eventbus-kotlin:1.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
}

tasks {
    getByName<ProcessResources>("processResources") {
        inputs.property("version", version)

        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to version))
        }
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    withType(KotlinCompile::class) {
        kotlinOptions.jvmTarget = "17"
    }
}

