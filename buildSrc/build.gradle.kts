import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    groovy
    `kotlin-dsl`
    `groovy-gradle-plugin`
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()

    maven {
        name = "Quilt"
        url = uri("https://maven.quiltmc.org/repository/release")
    }
    maven {
        name = "Quilt Snapshot"
        url = uri("https://maven.quiltmc.org/repository/snapshot")
    }
    // Note: temporary mavens until Quilt buildtools and loader are ready.
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    // Base Kotlin dependencies
    val kotlinVersion = "1.7.0"
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", "1.20.0")
    implementation(gradleApi())
    implementation(localGroovy())

    // Modding dependencies
    implementation("org.quiltmc:loom:0.12.+")
    implementation("org.quiltmc:quilt-gradle-licenser:1.1.+")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            languageVersion = "1.7"
        }
    }
}

