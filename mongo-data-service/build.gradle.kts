import com.palantir.gradle.docker.DockerExtension
import org.gradle.jvm.tasks.Jar

val reactorKotlinExtensions by project

apply {
    plugin("com.palantir.docker")
}

val jar: Jar by tasks
docker {
    name = "${group}/${jar.baseName}:${jar.version}"
    files(jar.outputs)
    setDockerfile(file("src/main/docker/Dockerfile"))
    buildArgs(mapOf(
            "JAR_NAME" to jar.archiveName,
            "PORT"   to  "8080",
            "JAVA_OPTS" to "-Xms512m -Xmx1024m"
    ))
    pull(true)
    dependsOn(tasks.findByName("build"))
}

dependencies {
    compile(project(":shared"))

    compile("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

    compile("io.projectreactor:reactor-kotlin-extensions:$reactorKotlinExtensions")
    testCompile("io.projectreactor.addons:reactor-test")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

/**
 * Configures the [docker][DockerExtension] project extension.
 */
val Project.docker get() = extensions.getByName("docker") as DockerExtension

fun Project.docker(configure: DockerExtension.() -> Unit): Unit = extensions.configure("docker", configure)