plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.serialization") version "1.9.21"
    application
}

application {
    mainClass.set("app.MainKt")
}

kotlin {
    jvmToolchain(21)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("io.mockk:mockk:1.13.8")
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Main-Class" to "app.MainKt"
        )
    }
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("task-manager")
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)

    manifest {
        attributes["Main-Class"] = "app.MainKt"
    }
}

tasks.build {
    dependsOn(tasks.test)
}

tasks.test {
    useJUnitPlatform()
}


