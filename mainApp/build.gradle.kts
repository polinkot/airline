import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		jcenter()
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
//	id("com.github.ben-manes.versions") version "0.36.0"
}

//tasks {
//	val dependencyUpdate =
//			named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates")
//
//	//dependencyUpdate {
//	//	revision = "release"
//	//	outputFormatter = "txt"
//	//	checkForGradleUpdate = true
//	//	outputDir = "$buildDir/reports/dependencies"
//	//	reportfileName = "updates"
//	//}
//
//	dependencyUpdate.configure {
//
//		fun isNonStable(version: String): Boolean {
//			val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
//			val regex = "^[0-9,.v-]+(-r)?$".toRegex()
//			val isStable = stableKeyword || regex.matches(version)
////			return isStable.not()
//			return false
//		}
//
//		rejectVersionIf {
//			isNonStable(candidate.version) && !isNonStable(currentVersion)
//		}
//	}
//
//	check {
//		finalizedBy(dependencyUpdate)
//	}
//}

tasks.test {
	useJUnitPlatform()
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

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
	allWarningsAsErrors = true
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}