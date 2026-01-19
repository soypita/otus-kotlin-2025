pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "otus-kotlin-2025"

includeBuild("lessons")
includeBuild("project-be")