import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import http.v1.createBook
import http.v1.deleteBook
import http.v1.readBook
import http.v1.searchBook
import http.v1.updateBook

fun Route.v1Book(appSettings: BookshelfAppSettings) {
    route("books") {
        post("create") {
            call.createBook(appSettings)
        }
        post("read") {
            call.readBook(appSettings)
        }
        post("update") {
            call.updateBook(appSettings)
        }
        post("delete") {
            call.deleteBook(appSettings)
        }
        post("search") {
            call.searchBook(appSettings)
        }
    }
}

