rootProject.name = "book-shelf-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("book-shelf-api-v1-jackson")
include("book-shelf-common")
include("book-shelf-api-v1-mappers")
include("book-shelf-app-ktor-kafka")
include("book-shelf-app-common")
include("book-shelf-biz")
include("book-shelf-stubs")
include("book-shelf-api-log1")
