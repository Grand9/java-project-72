plugins {
    id("java")
    id("checkstyle")
    id("application")
    id("jacoco")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("io.javalin:javalin:6.2.0")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("gg.jte:jte:3.1.12")
    implementation("io.javalin:javalin-rendering:6.2.0")

    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("net.datafaker:datafaker:2.0.2")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.apache.commons:commons-lang3:3.13.0")
}

application {
    mainClass.set("hexlet.code.App")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
