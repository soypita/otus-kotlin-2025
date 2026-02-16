package ru.otus.otuskotlin.bookshelf.api.v1

import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookResponseObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookStatus
import ru.otus.otuskotlin.bookshelf.api.v1.models.IResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = BookCreateResponse(
        book = BookResponseObject(
            title = "book title",
            author = "author",
            status = BookStatus.WANT_TO_READ,
            rating = "1",
            reviewText = "test",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"book title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as BookCreateResponse

        assertEquals(response, obj)
    }
}
