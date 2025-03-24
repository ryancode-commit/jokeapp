// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra["agp_version"] = "8.4.1"
    extra["kotlin_version"] = "1.8.0"
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://maven.aliyun.com/repository/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${property("agp_version")}")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${property("kotlin_version")}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.aliyun.com/repository/public")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
