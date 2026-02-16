package ru.otus.otuskotlin.bookshelf.api.v1

import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDebug
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugMode
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugStubs
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookStatus
import ru.otus.otuskotlin.bookshelf.api.v1.models.IRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = BookCreateRequest(
        debug = BookDebug(
            mode = BookRequestDebugMode.STUB,
            stub = BookRequestDebugStubs.BAD_TITLE
        ),
        book = BookCreateObject(
            title = "book title",
            author = "author",
            status = BookStatus.WANT_TO_READ,
            rating = "1",
            reviewText = "test",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"book title\""))
        assertContains(json, Regex("\"author\":\\s*\"author\""))
        assertContains(json, Regex("\"status\":\\s*\"want_to_read\""))
        assertContains(json, Regex("\"rating\":\\s*\"1\""))
        assertContains(json, Regex("\"reviewText\":\\s*\"test\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as BookCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"book": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, BookCreateRequest::class.java)

        assertEquals(null, obj.book)
    }
}
