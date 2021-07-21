val parentProjectDir = projectDir

plugins {
    id(Plugins.kotlin) version PluginVers.kotlin apply false
	id(Plugins.detekt) version PluginVers.detekt
	id(Plugins.update_dependencies) version PluginVers.update_dependencies
}

subprojects {

    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
    }

    apply {
		plugin(Plugins.kotlin)
        plugin(Plugins.detekt)
        plugin("jacoco")
		plugin(Plugins.update_dependencies)
    }

    detekt {
        config = files("$parentProjectDir/config/detekt/config.yml")
        buildUponDefaultConfig = true
        input = files("src/main/kotlin", "src/test/kotlin")

        reports {
            html.enabled = true
        }

        dependencies {
            detektPlugins("${Plugins.detekt_formatting}:${PluginVers.detekt_formatting}")
        }
    }

    configurations.all {
        resolutionStrategy {
            eachDependency {
//                requested.version?.contains("(?i)snapshot|rc".toRegex())?.let {
                requested.version?.contains("(?i)snapshot".toRegex())?.let {
                    if (it) {
                        throw GradleException("Snapshot or RC found: ${requested.name} ${requested.version}")
                    }
                }
            }
        }
    }

    tasks {
		val dependencyUpdate =
				named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates")

		dependencyUpdate {
			revision = "release"
			outputFormatter = "txt"
			checkForGradleUpdate = true
			outputDir = "$buildDir/reports/dependencies"
			reportfileName = "updates"
		}

		dependencyUpdate.configure {

			fun isNonStable(version: String): Boolean {
				val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
				val regex = "^[0-9,.v-]+(-r)?$".toRegex()
				val isStable = stableKeyword || regex.matches(version)
				return isStable.not()
			}

			rejectVersionIf {
				isNonStable(candidate.version) && !isNonStable(currentVersion)
			}
		}

        val check = named<DefaultTask>("check")

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
//                        minimum = BigDecimal("0.8")
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
			finalizedBy(dependencyUpdate)
            finalizedBy(jacocoTestReport)
        }

        val failOnWarning = project.properties["allWarningsAsErrors"] != null && project
                .properties["allWarningsAsErrors"] == "true"

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                allWarningsAsErrors = failOnWarning
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
