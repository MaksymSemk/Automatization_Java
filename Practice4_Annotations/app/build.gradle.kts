plugins {
    id("application")
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation (project(":annotations"))
    implementation (project(":Validator"))
    annotationProcessor (project(":processor"))
}

application {
    mainClass.set("org.example.AppMain")
}

tasks.test {
    useJUnitPlatform()
}