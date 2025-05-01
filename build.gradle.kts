plugins {
  id("java")
  id("application")
  id("org.graalvm.buildtools.native") version "0.10.5"
}

application {
  mainClass = "com.github.lorenzbaier.DemoApp"
}

tasks.withType<ProcessResources> {
  if (!project.hasProperty("includeReflectConfig")) {
    exclude("**/*-config.json")
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.github.peter-gergely-horvath:windpapi4j:2.1.0")
}

graalvmNative {
  binaries.all {
  }
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

