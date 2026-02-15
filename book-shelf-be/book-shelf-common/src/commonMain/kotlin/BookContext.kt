package ru.otus.otuskotlin.bookshelf.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.bookshelf.common.models.*
import ru.otus.otuskotlin.bookshelf.common.stubs.BookStubs

data class BookContext(
    var command: BookCommand = BookCommand.NONE,
    var state: BookState = BookState.NONE,
    val errors: MutableList<BookError> = mutableListOf(),

    var workMode: BookWorkMode = BookWorkMode.PROD,
    var stubCase: BookStubs = BookStubs.NONE,

    var requestId: BookRequestId = BookRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var bookRequest: Book = Book(),
    var bookFilterRequest: BookFilter = BookFilter(),

    var bookResponse: Book = Book(),
    var booksResponse: MutableList<Book> = mutableListOf(),
)
