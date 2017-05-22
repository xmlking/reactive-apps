val reactorKotlinExtensions by project

apply {
    plugin("org.springframework.boot")
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.jetbrains.kotlin.plugin.spring")
//    plugin("org.jetbrains.kotlin.plugin.noarg")
//    plugin("org.jetbrains.kotlin.plugin.jpa")
    plugin("io.spring.dependency-management")
    plugin("com.palantir.docker")
    from("docker.gradle")
}

// FIXME : move noarg plugin to root project build.gradle.kts
plugins {
    id("org.jetbrains.kotlin.plugin.noarg") version "1.1.2-2"
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
    compile("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testCompile("org.springframework.boot:spring-boot-starter-test")

    compile("io.projectreactor:reactor-kotlin-extensions:$reactorKotlinExtensions")
    testCompile("io.projectreactor.addons:reactor-test")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}