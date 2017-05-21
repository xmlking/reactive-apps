val kotlinVersion by project
val springBootVersion by project
val reactorKotlinExtensions by project
val springDependencyManagement by project
val dockerPluginVersion by project

buildscript {
    val kotlinVersion = "1.1.2-2"
    val springBootVersion = "2.0.0.M1"

    repositories {
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

apply {
    plugin("org.springframework.boot")
    from("docker.gradle")
}

plugins {
    val kotlinVersion = "1.1.2-2"
    val springDependencyManagement = "1.0.2.RELEASE"
    val dockerPluginVersion = "0.13.0"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
//    id("org.jetbrains.kotlin.plugin.jpa"") version kotlinVersion
    id("io.spring.dependency-management") version springDependencyManagement
    id("com.palantir.docker") version dockerPluginVersion
}

noArg {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

dependencies {
    compile(kotlinModule("stdlib-jre8"))
    compile(kotlinModule("reflect"))

    compile(project(":commons"))

    compile("org.springframework.boot:spring-boot-starter-webflux") {
        exclude(module = "hibernate-validator")
    }
    compileOnly("org.springframework:spring-context-indexer")
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    runtime("org.webjars:bootstrap:3.3.7")
    runtime("org.webjars:highcharts:5.0.8")
    testCompile("org.springframework.boot:spring-boot-starter-test")

    compile("io.projectreactor:reactor-kotlin-extensions:$reactorKotlinExtensions")
    testCompile("io.projectreactor.addons:reactor-test")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}