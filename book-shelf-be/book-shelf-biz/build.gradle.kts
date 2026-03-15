plugins {
    id("build-jvm")
}


dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(projects.bookShelfCommon)
    implementation(projects.bookShelfStubs)
}
