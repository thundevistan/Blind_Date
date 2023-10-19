pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
		maven("https://jitpack.io")     // CardStackView Library 에러 해결 부분
	}
}

rootProject.name = "Blind Date"
include(":app")
