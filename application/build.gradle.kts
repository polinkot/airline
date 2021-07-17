plugins {
	id(Plugins.spring_boot) version PluginVers.spring_boot
	id(Plugins.spring_dependency_management) version PluginVers.spring_dependency_management
	id(Plugins.spring_kotlin) version PluginVers.spring_kotlin
}

dependencies {
	// kotlin
	implementation(kotlin("stdlib-jdk8"))
	implementation(Libs.kotlin_reflect)

	// jackson
	implementation(Libs.jackson_kotlin)

	// spring
	implementation(Libs.spring_boot_starter_web)

	// tests
	testImplementation(Libs.spring_boot_starter_test) {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}