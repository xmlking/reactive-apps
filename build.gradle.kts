import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val version by project
val kotlinVersion by project

buildscript {
    repositories {
        gradleScriptKotlin()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin"))

    }
}

plugins {
    base
}

apply {
    plugin("idea")
}

allprojects {
    group = "org.xmlking.mapr"
    version = version

    repositories {
        gradleScriptKotlin()
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
        maven { setUrl("https://repo.spring.io/snapshot") }
    }
}

// Configure all KotlinCompile tasks on each sub-project
subprojects {
    tasks.withType<KotlinCompile> {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}

