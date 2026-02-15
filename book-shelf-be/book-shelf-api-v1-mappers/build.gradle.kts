plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.bookShelfApiV1Jackson)
    implementation(projects.bookShelfCommon)

    testImplementation(kotlin("test-junit"))
}
