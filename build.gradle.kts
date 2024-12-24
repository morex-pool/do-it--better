plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.2"
}

group = "org.mortezapouladi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

intellij {
    version.set("2024.1")
    type.set("IC")
    plugins.set(listOf("java"))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    prepareSandbox {
        dependsOn(processResources)
    }
}