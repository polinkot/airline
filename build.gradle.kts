import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val parentProjectDir = projectDir

plugins {
    kotlin("jvm") version Global.kotlin apply false
    id("io.gitlab.arturbosch.detekt") version Vers.detektVersion
//    id("com.github.ben-manes.versions") version "0.36.0"
}

/**
 * Project configuration by properties and environment
 */
fun envConfig() = object : ReadOnlyProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? =
            if (ext.has(property.name)) {
                ext[property.name] as? String
            } else {
                System.getenv(property.name)
            }
}

subprojects {
    group = "com.example.airline"
}


//plugins {
//	id(Plugins.kotlin) version PluginVers.kotlin apply true
//	id(Plugins.detekt) version PluginVers.detekt
//	id(Plugins.update_dependencies) version PluginVers.update_dependencies
//	id(Plugins.owasp_dependencies) version PluginVers.owasp_dependencies
//}

allprojects {

    configurations.all {
        resolutionStrategy {
            eachDependency {
                requested.version?.contains("snapshot", true)?.let {
                    if (it) {
                        throw GradleException("Snapshot found: ${requested.name} ${requested.version}")
                    }
                }
            }
        }
    }

    apply {
//		plugin("java")
//		plugin(Plugins.kotlin)
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("jacoco")
//		plugin(Plugins.update_dependencies)
//		plugin("com.github.ben-manes.versions")
//		plugin(Plugins.owasp_dependencies)
    }

    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
    }

    detekt {
        config = files("$parentProjectDir/detekt/config.yml")
        buildUponDefaultConfig = true
        input = files("src/main/kotlin", "src/test/kotlin")

        reports {
            html.enabled = true
        }

//        dependencies {
//            detektPlugins("${"io.gitlab.arturbosch.detekt:detekt-formatting"}:${"1.16.0"}")
//        }
    }

    tasks {

        val check = named<DefaultTask>("check")

//		val dependencyUpdate =
//				named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates")
//
//		dependencyUpdate {
//			revision = "release"
//			outputFormatter = "txt"
//			checkForGradleUpdate = true
//			outputDir = "$buildDir/reports/dependencies"
//			reportfileName = "updates"
//		}
//
//		dependencyUpdate.configure {
//
//			fun isNonStable(version: String): Boolean {
//				val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
//				val regex = "^[0-9,.v-]+(-r)?$".toRegex()
//				val isStable = stableKeyword || regex.matches(version)
//				return isStable.not()
//			}
//
//			rejectVersionIf {
//				isNonStable(candidate.version) && !isNonStable(currentVersion)
//			}
//		}

        val jacocoTestReport = named<JacocoReport>("jacocoTestReport")
        val jacocoTestCoverageVerification = named<JacocoCoverageVerification>("jacocoTestCoverageVerification")

        jacocoTestReport {
            dependsOn(check)
            finalizedBy(jacocoTestCoverageVerification)
        }

        jacocoTestCoverageVerification {
            dependsOn(jacocoTestReport)
        }

        withType<JacocoCoverageVerification> {
            violationRules {
                rule {
                    limit {
                        minimum = BigDecimal("0.8")
                    }
                }
            }

            afterEvaluate {
                classDirectories.setFrom(files(classDirectories.files.map {
                    fileTree(it).apply {
                        exclude("com/example/airline/AirlineApplication*")
                    }
                }))
            }
        }

        withType<JacocoReport> {
            afterEvaluate {
                classDirectories.setFrom(files(classDirectories.files.map {
                    fileTree(it).apply {
                        exclude("com/example/airline/AirlineApplication*")
                    }
                }))
            }
        }

        check {
            finalizedBy(jacocoTestReport)
//			finalizedBy(dependencyUpdate)
        }

        val failOnWarning = project.properties["allWarningsAsErrors"] != null && project
                .properties["allWarningsAsErrors"] == "true"

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                jvmTarget = JavaVersion.VERSION_11.toString()
                allWarningsAsErrors = failOnWarning
                freeCompilerArgs = listOf("-Xjvm-default=enable")
            }
        }

        withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:all")
        }

        withType<Test> {
            useJUnitPlatform()

            testLogging {
                events(
                        org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
                )
                showStandardStreams = true
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }
        }
    }
}
