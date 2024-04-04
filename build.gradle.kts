plugins {
    java
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("org.jetbrains:annotations:24.0.0")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    implementation("com.speedment.jpastreamer:jpastreamer-core:3.0.4")
    implementation("org.postgresql:postgresql")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:3.1.5")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")

}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Copy> {
    exclude("**/.DS_Store")
}
