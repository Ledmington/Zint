plugins {
    application
}

group = "zint"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

    implementation("com.google.guava:guava:30.1.1-jre")
}

application {
    mainClass.set("zint.Zint")
}

tasks.test {
    useJUnitPlatform()
}

tasks.create<JavaExec>("runOnConsole") {
    group = "run"
    standardInput = System.`in`
    classpath = project.sourceSets.main.get().runtimeClasspath
    mainClass.set("zint.Zint")
}