plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
    id("build-docker")
}

docker {
    buildContext = "."
    imageTag = "${project.version}"

    // JVM образ
    images.register("Jvm") {
        buildContext = project.layout.buildDirectory.dir("docker-jvm").get().toString()
        dockerFile = "Dockerfile"
        dependsOnTask = "jvmJar"
    }
}


dependencies {
    implementation(libs.kafka.client)
    implementation(libs.kotlinx.atomicfu)

    implementation(kotlin("stdlib-common"))
    implementation(projects.bookShelfAppCommon)
    implementation(projects.bookShelfCommon)
    implementation(projects.bookShelfApiV1Jackson)
    implementation(libs.bookshelf.logs.logback)
    implementation(projects.bookShelfBiz)
    implementation(projects.bookShelfApiV1Mappers)

    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.server.headers.default)
    implementation(libs.ktor.server.calllogging)
    implementation(libs.ktor.server.headers.response)
    implementation(libs.ktor.server.headers.caching)

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(libs.ktor.server.test)
    testImplementation(libs.ktor.client.negotiation)
}
