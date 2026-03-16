package http.v1

import BookshelfAppSettings
import io.ktor.server.application.*
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDeleteRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDeleteResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookReadRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookReadResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookSearchRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookSearchResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookUpdateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookUpdateResponse
import kotlin.reflect.KClass

val clCreate: KClass<*> = ApplicationCall::createBook::class
suspend fun ApplicationCall.createBook(appSettings: BookshelfAppSettings) =
    processV1<BookCreateRequest, BookCreateResponse>(appSettings, clCreate,"create")

val clRead: KClass<*> = ApplicationCall::readBook::class
suspend fun ApplicationCall.readBook(appSettings: BookshelfAppSettings) =
    processV1<BookReadRequest, BookReadResponse>(appSettings, clRead, "read")

val clUpdate: KClass<*> = ApplicationCall::updateBook::class
suspend fun ApplicationCall.updateBook(appSettings: BookshelfAppSettings) =
    processV1<BookUpdateRequest, BookUpdateResponse>(appSettings, clUpdate, "update")

val clDelete: KClass<*> = ApplicationCall::deleteBook::class
suspend fun ApplicationCall.deleteBook(appSettings: BookshelfAppSettings) =
    processV1<BookDeleteRequest, BookDeleteResponse>(appSettings, clDelete, "delete")

val clSearch: KClass<*> = ApplicationCall::searchBook::class
suspend fun ApplicationCall.searchBook(appSettings: BookshelfAppSettings) =
    processV1<BookSearchRequest, BookSearchResponse>(appSettings, clSearch, "search")
