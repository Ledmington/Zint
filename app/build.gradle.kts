group = "zint"
version = "0.1.0"

plugins {
    application
    java
    checkstyle
    pmd
    jacoco
    antlr
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.antlr:antlr4:4.10.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-custom.xsl")
    }
}

checkstyle {
    toolVersion = "8.15"
    configFile = file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = true
}

pmd {
    isIgnoreFailures = true
}

jacoco {
    toolVersion = "0.8.2"
}

application {
    mainClass.set("zint.Zint")
}