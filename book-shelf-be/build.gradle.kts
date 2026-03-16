plugins {
    id("build-jvm") apply false
    id("build-kmp") apply false
}
group = "ru.otus.otuskotlin.bookshelf"
version = "0.0.1"

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-bookshelf-v1.yaml").toString())
    set("spec-log1", specDir.file("specs-bookshelf-log1.yaml").toString())
}


tasks {
    register("build" ) {
        group = "build"
    }
    register("check" ) {
        group = "verification"
        subprojects.forEach { proj ->
            println("PROJ $proj")
            proj.getTasksByName("check", false).also {
                this@register.dependsOn(it)
            }
        }
    }
}
