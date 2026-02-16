import org.junit.Test
import ru.otus.otuskotlin.bookshelf.api.v1.models.*
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.models.*
import ru.otus.otuskotlin.bookshelf.common.models.BookStatus as ModelBookStatus
import ru.otus.otuskotlin.bookshelf.common.stubs.BookStubs
import ru.otus.otuskotlin.bookshelf.mappers.v1.toTransportBook
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MappersV1ToTransportTest {

    @Test
    fun toTransportCreate() {
        val context = BookContext(
            command = BookCommand.CREATE,
            state = BookState.RUNNING,
            errors = mutableListOf(),
            workMode = BookWorkMode.PROD,
            stubCase = BookStubs.NONE,
            requestId = BookRequestId("123"),
            bookResponse = Book(
                id = BookId("456"),
                title = "Book Title",
                author = "Book Author",
                status = ModelBookStatus.READ,
                rating = "5",
                reviewText = "Great Book!",
                userId = UserId("789"),
                lock = BookLock("123-234-abc-ABC"),
                permissionsClient = mutableSetOf(
                    BookPermissionClient.READ,
                    BookPermissionClient.UPDATE,
                    BookPermissionClient.DELETE,
                )
            )
        )

        val res = context.toTransportBook() as BookCreateResponse

        assertEquals("create", res.responseType)
        assertEquals(ResponseResult.SUCCESS, res.result)
        assertNull(res.errors?.size)
        assertEquals("456", res.book?.id)
        assertEquals("Book Title", res.book?.title)
        assertEquals("Book Author", res.book?.author)
        assertEquals(BookStatus.READ, res.book?.status)
        assertEquals("5", res.book?.rating)
        assertEquals("Great Book!", res.book?.reviewText)
        assertEquals("789", res.book?.userId)
        assertEquals("123-234-abc-ABC", res.book?.lock)
        assertEquals(3, res.book?.permissions?.size)
        assert(res.book?.permissions?.contains(BookPermissions.READ) ?: false)
        assert(res.book?.permissions?.contains(BookPermissions.UPDATE) ?: false)
        assert(res.book?.permissions?.contains(BookPermissions.DELETE) ?: false)
    }

    @Test
    fun toTransportRead() {
        val context = BookContext(
            command = BookCommand.READ,
            state = BookState.RUNNING,
            errors = mutableListOf(),
            workMode = BookWorkMode.PROD,
            stubCase = BookStubs.NONE,
            requestId = BookRequestId("123"),
            bookResponse = Book(
                id = BookId("456"),
                title = "Book Title",
                author = "Book Author",
                status = ModelBookStatus.READ,
                rating = "5",
                reviewText = "Great Book!",
                userId = UserId("789"),
                lock = BookLock("123-234-abc-ABC"),
                permissionsClient = mutableSetOf(
                    BookPermissionClient.READ,
                )
            )
        )

        val res = context.toTransportBook() as BookReadResponse

        assertEquals("read", res.responseType)
        assertEquals(ResponseResult.SUCCESS, res.result)
        assertNull(res.errors?.size)
        assertEquals("456", res.book?.id)
        assertEquals("Book Title", res.book?.title)
        assertEquals("Book Author", res.book?.author)
        assertEquals(BookStatus.READ, res.book?.status)
        assertEquals("5", res.book?.rating)
        assertEquals("Great Book!", res.book?.reviewText)
        assertEquals("789", res.book?.userId)
        assertEquals("123-234-abc-ABC", res.book?.lock)
        assertEquals(1, res.book?.permissions?.size)
        assert(res.book?.permissions?.contains(BookPermissions.READ) ?: false)
    }

    @Test
    fun toTransportUpdate() {
        val context = BookContext(
            command = BookCommand.UPDATE,
            state = BookState.RUNNING,
            errors = mutableListOf(),
            workMode = BookWorkMode.PROD,
            stubCase = BookStubs.NONE,
            requestId = BookRequestId("123"),
            bookResponse = Book(
                id = BookId("456"),
                title = "Book Title",
                author = "Book Author",
                status = ModelBookStatus.READ,
                rating = "5",
                reviewText = "Great Book!",
                userId = UserId("789"),
                lock = BookLock("123-234-abc-ABC"),
                permissionsClient = mutableSetOf(
                    BookPermissionClient.READ,
                    BookPermissionClient.UPDATE,
                )
            )
        )

        val res = context.toTransportBook() as BookUpdateResponse

        assertEquals("update", res.responseType)
        assertEquals(ResponseResult.SUCCESS, res.result)
        assertNull(res.errors?.size)
        assertEquals("456", res.book?.id)
        assertEquals("Book Title", res.book?.title)
        assertEquals("Book Author", res.book?.author)
        assertEquals(BookStatus.READ, res.book?.status)
        assertEquals("5", res.book?.rating)
        assertEquals("Great Book!", res.book?.reviewText)
        assertEquals("789", res.book?.userId)
        assertEquals("123-234-abc-ABC", res.book?.lock)
        assertEquals(2, res.book?.permissions?.size)
        assert(res.book?.permissions?.contains(BookPermissions.READ) ?: false)
        assert(res.book?.permissions?.contains(BookPermissions.UPDATE) ?: false)
    }

    @Test
    fun toTransportDelete() {
        val context = BookContext(
            command = BookCommand.DELETE,
            state = BookState.RUNNING,
            errors = mutableListOf(),
            workMode = BookWorkMode.PROD,
            stubCase = BookStubs.NONE,
            requestId = BookRequestId("123"),
            bookResponse = Book(
                id = BookId("456"),
            )
        )

        val res = context.toTransportBook() as BookDeleteResponse

        assertEquals("delete", res.responseType)
        assertEquals(ResponseResult.SUCCESS, res.result)
        assertNull(res.errors?.size)
        assertEquals("456", res.book?.id)
    }

    @Test
    fun toTransportSearch() {
        val context = BookContext(
            command = BookCommand.SEARCH,
            state = BookState.RUNNING,
            errors = mutableListOf(),
            workMode = BookWorkMode.PROD,
            stubCase = BookStubs.NONE,
            requestId = BookRequestId("123"),
            booksResponse = mutableListOf(
                Book(
                    id = BookId("456"),
                    title = "Book Title 1",
                    author = "Book Author 1",
                    status = ModelBookStatus.READ,
                ),
                Book(
                    id = BookId("789"),
                    title = "Book Title 2",
                    author = "Book Author 2",
                    status = ModelBookStatus.READING,
                )
            )
        )

        val res = context.toTransportBook() as BookSearchResponse

        assertEquals("search", res.responseType)
        assertEquals(ResponseResult.SUCCESS, res.result)
        assertNull(res.errors?.size)
        assertEquals(2, res.books?.size)
        assertEquals("456", res.books?.first()?.id)
        assertEquals("Book Title 1", res.books?.first()?.title)
        assertEquals("Book Author 1", res.books?.first()?.author)
        assertEquals(BookStatus.READ, res.books?.first()?.status)
        assertEquals("789", res.books?.last()?.id)
        assertEquals("Book Title 2", res.books?.last()?.title)
        assertEquals("Book Author 2", res.books?.last()?.author)
        assertEquals(BookStatus.READING, res.books?.last()?.status)
    }
}
