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
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

apply {
    plugin("org.springframework.boot")
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
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

dependencies {
    compile(kotlinModule("stdlib"))
    compile(kotlinModule("reflect"))

    compile(project(":commons"))

    compile("org.springframework.boot:spring-boot-starter-webflux") {
        exclude(module = "hibernate-validator")
    }
    compileOnly("org.springframework:spring-context-indexer")
    compile("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testCompile("org.springframework.boot:spring-boot-starter-test")

    compile("io.projectreactor:reactor-kotlin-extensions:$reactorKotlinExtensions")
    testCompile("io.projectreactor.addons:reactor-test")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}