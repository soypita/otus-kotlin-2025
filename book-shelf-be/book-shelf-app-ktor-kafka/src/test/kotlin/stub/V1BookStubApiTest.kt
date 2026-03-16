package ru.otus.otuskotlin.marketplace.app.ktor.stub

import BookshelfAppSettings
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import http.restApiModule
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDebug
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDeleteObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDeleteRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDeleteResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookReadObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookReadRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookReadResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugMode
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugStubs
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookSearchFilter
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookSearchRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookSearchResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookStatus
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookUpdateObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookUpdateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookUpdateResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.IRequest
import ru.otus.otuskotlin.bookshelf.common.BookShelfCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class V1BookStubApiTest {
    @Test
    fun create() = v1TestApplication(
        func = "create",
        request = BookCreateRequest(
            book = BookCreateObject(
                title = "Test Book",
                author = "Test Author",
                status = BookStatus.WANT_TO_READ
            ),
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.book?.id)
    }

    @Test
    fun read() = v1TestApplication(
        func = "read",
        request = BookReadRequest(
            book = BookReadObject("123"),
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.book?.id)
    }

    @Test
    fun update() = v1TestApplication(
        func = "update",
        request = BookUpdateRequest(
            book = BookUpdateObject(
                id = "123",
                status = BookStatus.READ,
            ),
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.book?.id)
    }

    @Test
    fun delete() = v1TestApplication(
        func = "delete",
        request = BookDeleteRequest(
            book = BookDeleteObject(
                id = "123",
            ),
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.book?.id)
    }

    @Test
    fun search() = v1TestApplication(
        func = "search",
        request = BookSearchRequest(
            bookFilter = BookSearchFilter(),
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.books?.first()?.id)
    }

    private fun v1TestApplication(
        func: String,
        request: IRequest,
        function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { restApiModule(BookshelfAppSettings(corSettings = BookShelfCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        val response = client.post("/v1/books/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}
