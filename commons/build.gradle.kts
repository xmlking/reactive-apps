val kotlinVersion by project
val springBootVersion by project
val reactorKotlinExtensions by project
val springDependencyManagement by project

buildscript {
    val kotlinVersion = "1.1.2-2"
    val springBootVersion = "2.0.0.BUILD-SNAPSHOT"

    repositories {
        mavenCentral()
        maven { setUrl("https://repo.spring.io/snapshot") }
        maven { setUrl("https://repo.spring.io/milestone") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    }
}

plugins {
    val kotlinVersion = "1.1.2-2"
    val springDependencyManagement = "1.0.2.RELEASE"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
//    id("org.jetbrains.kotlin.plugin.jpa"") version kotlinVersion
    id("io.spring.dependency-management") version springDependencyManagement
}

noArg {
    annotation("com.example.Quote")
}

dependencies {
    compile(kotlinModule("stdlib"))
    compile(kotlinModule("reflect"))
}
