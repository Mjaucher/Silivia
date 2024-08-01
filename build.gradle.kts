import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    id("fabric-loom") version "1.7-SNAPSHOT"
}

val minecraft = "1.21"
val kotlin = "2.0.0"

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
    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:$minecraft+build.1:v2")

    modImplementation("net.fabricmc:fabric-loader:0.15.11")
    modImplementation("meteordevelopment:meteor-client:0.5.8-SNAPSHOT")

    implementation("com.github.therealbush:eventbus-kotlin:1.0.1")
    implementation("com.github.Vatuu:discord-rpc:1.6.2")

    arrayOf("stdlib", "stdlib-jdk8", "reflect").forEach {
        implementation(kotlin(it, kotlin))
    }
}

base {
    archivesBaseName = "Silivia-v1"
}

tasks {

    getByName<ProcessResources>("processResources") {
        filesMatching("fabric.mod.json") {

            expand(mutableMapOf("version" to version))
        }
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    withType(KotlinCompile::class) {
        kotlinOptions.jvmTarget = "21"
    }
}

