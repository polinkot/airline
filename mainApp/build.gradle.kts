buildscript {
	repositories {
		mavenCentral()
		mavenLocal()
	}

	dependencies {
		classpath(Libs.kotlin_stdlib)
		classpath(Libs.kotlin_jdk8)
		classpath(Libs.kotlin_reflect)
	}
}

repositories {
	jcenter()
	mavenCentral()
	mavenLocal()
}

plugins {
	java
	kotlin("jvm")
	id("org.springframework.boot") version Vers.springBoot
	id("io.spring.dependency-management") version Vers.springDependencyVersion
	kotlin("plugin.spring") version Global.kotlin
}

dependencies {
	// kotlin
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlin:kotlin-reflect")

//	implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0-rc2")


	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}