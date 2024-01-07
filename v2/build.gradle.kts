import org.springframework.boot.gradle.tasks.bundling.BootJar

val log4jVersion: String by project
val junitVersion: String by project
val postgresVersion: String by project
val ktormVersion: String by project
val springVersion: String by project
val springCloudVersion: String by project

plugins {
	application
	kotlin("jvm") version "1.9.20"
	kotlin("kapt") version "1.9.20"
	id("com.github.johnrengelman.shadow") version "7.1.2"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.9.20"
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.jetbrains.kotlin.plugin.spring") version "1.9.20"
}

allprojects {
	apply(plugin = "com.github.johnrengelman.shadow")
	apply(plugin = "io.spring.dependency-management")

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:$springVersion")
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
		}
	}

	repositories {
		mavenCentral()
	}

	tasks.withType<AbstractArchiveTask> {
		isPreserveFileTimestamps = false
		isReproducibleFileOrder = true
	}

	tasks.withType<BootJar> {
		archiveClassifier.set("all")
	}
}
