import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val log4jVersion: String by project
val junitVersion: String by project
val postgresVersion: String by project
val ktormVersion: String by project

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

group = "com.mianeko"
version = "0.0.1-SNAPSHOT"

dependencies {
	implementation("org.postgresql:postgresql:$postgresVersion")

	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core")
	implementation("org.ktorm:ktorm-core:$ktormVersion")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
}

application {
	mainClass.set("com.mianeko.reservationservice.ReservationServiceApplicationKt")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
		jvmTarget = "17"
		languageVersion = "1.9"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
