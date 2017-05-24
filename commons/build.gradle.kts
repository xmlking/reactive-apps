val reactorKotlinExtensions by project

apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.jetbrains.kotlin.plugin.spring")
//    plugin("org.jetbrains.kotlin.plugin.noarg")
//    plugin("org.jetbrains.kotlin.plugin.jpa")
    plugin("io.spring.dependency-management")
}

plugins {
    id("org.jetbrains.kotlin.plugin.noarg") version "1.1.2-2"
}
noArg {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

dependencies {
    compile(kotlinModule("stdlib-jre8"))
    compile(kotlinModule("reflect"))
}
