package ru.otus.otuskotlin.bookshelf.mappers.v1

import ru.otus.otuskotlin.bookshelf.api.v1.models.*
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.models.*
import ru.otus.otuskotlin.bookshelf.common.stubs.BookStubs
import ru.otus.otuskotlin.bookshelf.mappers.v1.exceptions.UnknownRequestClass

fun BookContext.fromTransport(request: IRequest) = when (request) {
    is BookCreateRequest -> fromTransport(request)
    is BookReadRequest -> fromTransport(request)
    is BookUpdateRequest -> fromTransport(request)
    is BookDeleteRequest -> fromTransport(request)
    is BookSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun BookContext.fromTransport(request: BookCreateRequest) {
    command = BookCommand.CREATE
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    bookRequest = request.book?.toInternal() ?: Book()
}

private fun BookContext.fromTransport(request: BookReadRequest) {
    command = BookCommand.READ
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    bookRequest = request.book?.id.toBookWithId()
}

private fun BookContext.fromTransport(request: BookUpdateRequest) {
    command = BookCommand.UPDATE
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    bookRequest = request.book?.toInternal() ?: Book()
}

private fun BookContext.fromTransport(request: BookDeleteRequest) {
    command = BookCommand.DELETE
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    bookRequest = request.book?.toInternal() ?: Book()
}

private fun BookContext.fromTransport(request: BookSearchRequest) {
    command = BookCommand.SEARCH
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    bookFilterRequest = request.bookFilter.toInternal()
}

private fun BookCreateObject.toInternal(): Book = Book(
    title = this.title ?: "",
    author = this.author ?: "",
    status = this.status.fromTransport(),
    rating = this.rating ?: "",
    reviewText = this.reviewText ?: "",
)

private fun BookUpdateObject.toInternal(): Book = Book(
    id = this.id.toBookId(),
    title = this.title ?: "",
    author = this.author ?: "",
    status = this.status.fromTransport(),
    rating = this.rating ?: "",
    reviewText = this.reviewText ?: "",
    lock = this.lock.toBookLock(),
)

private fun BookSearchFilter?.toInternal(): BookFilter = BookFilter(
    title = this?.title ?: "",
    author = this?.author ?: "",
    rating = this?.rating ?: "",
    status = this?.status.fromTransport(),
)

private fun BookDeleteObject.toInternal(): Book = Book(
    id = this.id.toBookId(),
    lock = this.lock.toBookLock(),
)

private fun String?.toBookId() = this?.let { BookId(it) } ?: BookId.NONE
private fun String?.toBookWithId() = Book(id = this.toBookId())
private fun String?.toBookLock() = this?.let { BookLock(it) } ?: BookLock.NONE

private fun BookDebug?.transportToWorkMode(): BookWorkMode = when (this?.mode) {
    BookRequestDebugMode.PROD -> BookWorkMode.PROD
    BookRequestDebugMode.TEST -> BookWorkMode.TEST
    BookRequestDebugMode.STUB -> BookWorkMode.STUB
    null -> BookWorkMode.PROD
}

private fun BookDebug?.transportToStubCase(): BookStubs = when (this?.stub) {
    BookRequestDebugStubs.SUCCESS -> BookStubs.SUCCESS
    BookRequestDebugStubs.NOT_FOUND -> BookStubs.NOT_FOUND
    BookRequestDebugStubs.BAD_ID -> BookStubs.BAD_ID
    BookRequestDebugStubs.BAD_TITLE -> BookStubs.BAD_TITLE
    BookRequestDebugStubs.BAD_AUTHOR -> BookStubs.BAD_AUTHOR
    BookRequestDebugStubs.BAD_RATING -> BookStubs.BAD_RATING
    BookRequestDebugStubs.BAD_REVIEW_TEXT -> BookStubs.BAD_REVIEW_TEXT
    BookRequestDebugStubs.CANNOT_DELETE -> BookStubs.CANNOT_DELETE
    BookRequestDebugStubs.BAD_SEARCH_PARAMS -> BookStubs.BAD_SEARCH_PARAMS
    null -> BookStubs.NONE
    else -> BookStubs.NONE
}

private fun ru.otus.otuskotlin.bookshelf.api.v1.models.BookStatus?.fromTransport(): ru.otus.otuskotlin.bookshelf.common.models.BookStatus =
    when (this) {
        BookStatus.WANT_TO_READ -> ru.otus.otuskotlin.bookshelf.common.models.BookStatus.WANT_TO_READ
        BookStatus.READING -> ru.otus.otuskotlin.bookshelf.common.models.BookStatus.READING
        BookStatus.READ -> ru.otus.otuskotlin.bookshelf.common.models.BookStatus.READ
        else -> ru.otus.otuskotlin.bookshelf.common.models.BookStatus.NONE
    }
