import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.BookShelfCorSettings
import ru.otus.otuskotlin.bookshelf.common.models.BookState
import ru.otus.otuskotlin.marketplace.stubs.BookshelfBookStub

@Suppress("unused", "RedundantSuspendModifier")
class BookshelfBookProcessor(val corSettings: BookShelfCorSettings) {

    suspend fun exec(ctx: BookContext) {
        ctx.bookResponse = BookshelfBookStub.get()
        ctx.booksResponse = BookshelfBookStub.prepareSearchList().toMutableList()
        ctx.state = BookState.RUNNING
    }
}
