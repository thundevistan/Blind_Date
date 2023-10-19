import org.jetbrains.kotlin.konan.properties.Properties

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.google.gms.google-services")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
	namespace = "com.kotdev99.android.blinddate"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.kotdev99.android.blinddate"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		buildConfigField(
			"String",
			"FIREBASE_DATABASE_URL",
			properties.getProperty("FIREBASE_DATABASE_URL")
		)
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		viewBinding = true
		buildConfig = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

	// CardStackView Library
	implementation("com.github.yuyakaido:cardstackview:2.3.4")

	// Firebase
	implementation("com.google.firebase:firebase-auth:22.2.0")
	implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
	implementation("com.google.firebase:firebase-database-ktx")
}