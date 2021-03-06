plugins {
	id(Plugins.spring_boot) version PluginVers.spring_boot
	id(Plugins.spring_dependency_management) version PluginVers.spring_dependency_management
	id(Plugins.spring_kotlin) version PluginVers.spring_kotlin
}

dependencies {

	// project
	implementation(project(":common:types"))

	implementation(project(":leasing:domain"))
	implementation(project(":leasing:usecase"))
	implementation(project(":leasing:persistence"))
	implementation(project(":leasing:web"))

	implementation(project(":flight:domain"))
	implementation(project(":flight:usecase"))
	implementation(project(":flight:persistence"))
	implementation(project(":flight:web"))

	implementation(project(":maintenance:domain"))
	implementation(project(":maintenance:usecase"))
	implementation(project(":maintenance:persistence"))
	implementation(project(":maintenance:web"))

	implementation(project(":integration:payment"))

	// kotlin
	implementation(kotlin("stdlib-jdk8"))
	implementation(Libs.kotlin_reflect)

	// jackson
	implementation(Libs.jackson_kotlin)

	// arrow
	implementation(Libs.arrow)

	// spring
	implementation(Libs.spring_boot_starter_web)

	// test
	testImplementation(Libs.kotest_junit)
	testImplementation(Libs.kotest_arrow)
	testImplementation(Libs.junit_engine)
	testImplementation(Libs.junit_params)
	testImplementation(Libs.spring_boot_starter_test) {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation(Libs.arch_unit)
}