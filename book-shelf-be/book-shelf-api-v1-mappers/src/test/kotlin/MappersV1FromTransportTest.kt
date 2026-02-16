import org.junit.Test
import ru.otus.otuskotlin.bookshelf.api.v1.models.*
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.models.*
import ru.otus.otuskotlin.bookshelf.common.models.BookStatus as ModelBookStatus
import ru.otus.otuskotlin.bookshelf.common.stubs.BookStubs
import ru.otus.otuskotlin.bookshelf.mappers.v1.fromTransport
import kotlin.test.assertEquals

class MappersV1FromTransportTest {

    @Test
    fun fromTransportCreate() {
        val req = BookCreateRequest(
            requestType = "create",
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS,
            ),
            book = BookCreateObject(
                title = "Book Title",
                author = "Book Author",
                status = BookStatus.READ,
                rating = "5",
                reviewText = "Great Book!",
            ),
        )

        val context = BookContext()
        context.fromTransport(req)

        assertEquals(BookStubs.SUCCESS, context.stubCase)
        assertEquals(BookWorkMode.STUB, context.workMode)
        assertEquals(BookCommand.CREATE, context.command)
        assertEquals("Book Title", context.bookRequest.title)
        assertEquals("Book Author", context.bookRequest.author)
        assertEquals(ModelBookStatus.READ, context.bookRequest.status)
        assertEquals("5", context.bookRequest.rating)
        assertEquals("Great Book!", context.bookRequest.reviewText)
    }

    @Test
    fun fromTransportRead() {
        val req = BookReadRequest(
            requestType = "read",
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS,
            ),
            book = BookReadObject(
                id = "123"
            )
        )

        val context = BookContext()
        context.fromTransport(req)

        assertEquals(BookStubs.SUCCESS, context.stubCase)
        assertEquals(BookWorkMode.STUB, context.workMode)
        assertEquals(BookCommand.READ, context.command)
        assertEquals("123", context.bookRequest.id.asString())
    }

    @Test
    fun fromTransportUpdate() {
        val req = BookUpdateRequest(
            requestType = "update",
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS,
            ),
            book = BookUpdateObject(
                id = "123",
                title = "Book Title",
                author = "Book Author",
                status = BookStatus.READ,
                rating = "5",
                reviewText = "Great Book!",
                lock = "123-234-abc-ABC",
            ),
        )

        val context = BookContext()
        context.fromTransport(req)

        assertEquals(BookStubs.SUCCESS, context.stubCase)
        assertEquals(BookWorkMode.STUB, context.workMode)
        assertEquals(BookCommand.UPDATE, context.command)
        assertEquals("123", context.bookRequest.id.asString())
        assertEquals("Book Title", context.bookRequest.title)
        assertEquals("Book Author", context.bookRequest.author)
        assertEquals(ModelBookStatus.READ, context.bookRequest.status)
        assertEquals("5", context.bookRequest.rating)
        assertEquals("Great Book!", context.bookRequest.reviewText)
        assertEquals("123-234-abc-ABC", context.bookRequest.lock.asString())
    }

    @Test
    fun fromTransportDelete() {
        val req = BookDeleteRequest(
            requestType = "delete",
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS,
            ),
            book = BookDeleteObject(
                id = "123",
                lock = "123-234-abc-ABC",
            )
        )

        val context = BookContext()
        context.fromTransport(req)

        assertEquals(BookStubs.SUCCESS, context.stubCase)
        assertEquals(BookWorkMode.STUB, context.workMode)
        assertEquals(BookCommand.DELETE, context.command)
        assertEquals("123", context.bookRequest.id.asString())
        assertEquals("123-234-abc-ABC", context.bookRequest.lock.asString())
    }

    @Test
    fun fromTransportSearch() {
        val req = BookSearchRequest(
            requestType = "search",
            debug = BookDebug(
                mode = BookRequestDebugMode.STUB,
                stub = BookRequestDebugStubs.SUCCESS,
            ),
            bookFilter = BookSearchFilter(
                title = "Book",
                author = "Author",
                rating = "4",
                status = BookStatus.READING
            ),
        )

        val context = BookContext()
        context.fromTransport(req)

        assertEquals(BookStubs.SUCCESS, context.stubCase)
        assertEquals(BookWorkMode.STUB, context.workMode)
        assertEquals(BookCommand.SEARCH, context.command)
        assertEquals("Book", context.bookFilterRequest.title)
        assertEquals("Author", context.bookFilterRequest.author)
        assertEquals("4", context.bookFilterRequest.rating)
        assertEquals(ModelBookStatus.READING, context.bookFilterRequest.status)
    }
}
