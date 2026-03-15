import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDebug
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugMode
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugStubs
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookStatus
import ru.otus.otuskotlin.bookshelf.api.v1.models.IRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.IResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.ResponseResult
import ru.otus.otuskotlin.bookshelf.common.BookShelfCorSettings

import ru.otus.otuskotlin.bookshelf.mappers.v1.fromTransport
import ru.otus.otuskotlin.bookshelf.mappers.v1.toTransportBook
import ru.otus.otuskotlin.marketplace.IBookShelfAppSettings
import ru.otus.otuskotlin.marketplace.controllerHelper
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerTest {

    private val request = BookCreateRequest(
        book = BookCreateObject(
            title = "some book",
            author = "some author",
            status = BookStatus.WANT_TO_READ
        ),
        debug = BookDebug(mode = BookRequestDebugMode.STUB, stub = BookRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IBookShelfAppSettings = object : IBookShelfAppSettings {
        override val corSettings: BookShelfCorSettings = BookShelfCorSettings()
        override val processor: BookshelfBookProcessor = BookshelfBookProcessor(corSettings)
    }

    class TestApplicationCall(private val request: IRequest) {
        var res: IResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequest> receive(): T = request as T
        fun respond(res: IResponse) {
            this.res = res
        }
    }


    private suspend fun TestApplicationCall.createAdKtor(appSettings: IBookShelfAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<BookCreateRequest>()) },
            { toTransportBook() },
            ControllerTest::class,
            "controller-test"
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createAdKtor(appSettings) }
        val res = testApp.res as BookCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
