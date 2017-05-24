import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val version by project
val kotlinVersion by project
val springBootVersion by project
val reactorKotlinExtensions by project
val springDependencyManagement by project
val dockerPluginVersion by project

buildscript {
    val springBootVersion = "2.0.0.M1"

    repositories {
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

plugins {
    val kotlinVersion = "1.1.2-2"
    val springDependencyManagement = "1.0.2.RELEASE"
    val dockerPluginVersion = "0.13.0"

    base
    id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
//    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion apply false
//    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion apply false
    id("io.spring.dependency-management") version springDependencyManagement apply false
    id("com.palantir.docker") version dockerPluginVersion apply false
}

apply {
    plugin("idea")
}

allprojects {
    group = "reactive"
    version = version

    repositories {
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
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

