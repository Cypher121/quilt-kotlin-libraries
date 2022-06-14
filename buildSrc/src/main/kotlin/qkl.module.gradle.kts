import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Common buildscript for QKL in projects which depend on Minecraft, whether for the API or for running the game
 */

plugins {
    `maven-publish`
    id("org.quiltmc.loom")
    id("io.gitlab.arturbosch.detekt")
    id("org.quiltmc.gradle.licenser")
}

group = "org.quiltmc.quilt-kotlin-libraries"
version = Versions.QKL_VERSION

publishing {
    publications {
        if (project.name != "wrapper") {
            create<MavenPublication>("Maven") {
                artifactId = project.name
                if (project.name == "fatjar") {
                    artifactId = "quilt-kotlin-libraries"
                }
                version = project.version.toString()

                artifact(tasks.remapSourcesJar.get().archiveFile) {
                    builtBy(tasks.remapSourcesJar)
                    this.classifier = "sources"
                }
                artifact(tasks.remapJar.get().archiveFile) {
                    builtBy(tasks.remapJar)
                }
            }
        }
    }

    repositories {
        mavenLocal()
        if (System.getenv("MAVEN_URL") != null) {
            maven {
                setUrl(System.getenv("MAVEN_URL"))
                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
                name = "Maven"
            }
        } else if (System.getenv("SNAPSHOTS_URL") != null) {
            maven {
                setUrl(System.getenv("SNAPSHOTS_URL"))
                credentials {
                    username = System.getenv("SNAPSHOTS_USERNAME")
                    password = System.getenv("SNAPSHOTS_PASSWORD")
                }
                name = "Maven"
            }
        } else {
            mavenLocal()
        }
    }
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
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT_VERSION.version}")
    mappings(loom.layered {
        "org.quiltmc:quilt-mappings:${Versions.MINECRAFT_VERSION.version}+build.${Versions.MAPPINGS_BUILD}:v2"
    })

    implementation("org.quiltmc:quilt-loader:${Versions.LOADER_VERSION}")

    implementation("org.quiltmc:qsl:${Versions.QSL_VERSION}")
}

detekt {
    config = files("${rootProject.rootDir}/codeformat/detekt.yml")

    autoCorrect = true
}

license {
    rule(file("${rootProject.projectDir}/codeformat/HEADER"))
    include("**/*.kt")
}

tasks {
    processResources {
        inputs.property("version", version)
        filesMatching("quilt.mod.json") {
            expand(Pair("version", version))
        }
    }

    remapJar {
        archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
        dependsOn(remapSourcesJar)
    }

    remapSourcesJar {
        archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
    }
}
