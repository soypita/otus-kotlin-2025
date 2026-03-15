plugins {
    id("build-jvm")
}

dependencies {
    implementation(projects.bookShelfCommon)
    implementation(projects.bookShelfBiz)
    implementation(projects.bookShelfApiLog1)

    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)
    testImplementation(projects.bookShelfApiV1Jackson)
    testImplementation(projects.bookShelfApiV1Mappers)
    testImplementation(projects.bookShelfBiz)
}

