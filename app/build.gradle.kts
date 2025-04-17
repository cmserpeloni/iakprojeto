// Top-level build file
plugins {
    id("com.android.application") version "8.2.0" apply false  // For app module
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    // Add kapt for data binding (if needed for other modules)
    id("org.jetbrains.kotlin.kapt") version "1.9.20" apply false
}

// Configure common dependencies for all submodules
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}