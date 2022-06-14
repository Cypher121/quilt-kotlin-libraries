plugins {
    kotlin("jvm")
    alias(libs.plugins.git.hooks)
    id("qkl.module")
    `maven-publish`
}

group = "org.quiltmc"
version = Versions.QKL_VERSION
val projectVersion =
    project.version as String + if (System.getenv("SNAPSHOTS_URL") != null && System.getenv("MAVEN_URL") == null) "-SNAPSHOT" else ""

repositories {
    mavenCentral()
}

kotlin {
    // Enable explicit API mode, as this is a library
    explicitApi()
}

java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.toVersion(Versions.JAVA_VERSION)
    targetCompatibility = JavaVersion.toVersion(Versions.JAVA_VERSION)
}

gitHooks {
    // Before committing, check that licenses are all ready and the detekt checks have passed.
    setHooks(mapOf("pre-commit" to "checkLicenses detekt"))
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions {
            jvmTarget = Versions.JAVA_VERSION.toString()
            languageVersion = "1.7"
        }
    }

    remapJar {
        archiveBaseName.set("quilt-kotlin-libraries")
    }
}
