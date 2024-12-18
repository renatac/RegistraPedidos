// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.0"
}

buildscript {
    dependencies {
        classpath (libs.kotlin.parcelize)
        classpath(kotlin("gradle-plugin", version = "1.9.0"))
    }
}

