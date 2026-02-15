package ru.otus.otuskotlin.bookshelf.mappers.v1

import ru.otus.otuskotlin.bookshelf.api.v1.models.*
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.exceptions.UnknownBookCommand
import ru.otus.otuskotlin.bookshelf.common.models.Book
import ru.otus.otuskotlin.bookshelf.common.models.BookCommand
import ru.otus.otuskotlin.bookshelf.common.models.BookError
import ru.otus.otuskotlin.bookshelf.common.models.BookPermissionClient
import ru.otus.otuskotlin.bookshelf.common.models.BookState

fun BookContext.toTransportBook(): IResponse = when (val cmd = command) {
    BookCommand.CREATE -> toTransportCreate()
    BookCommand.READ -> toTransportRead()
    BookCommand.UPDATE -> toTransportUpdate()
    BookCommand.DELETE -> toTransportDelete()
    BookCommand.SEARCH -> toTransportSearch()
    BookCommand.NONE -> throw UnknownBookCommand(cmd)
}

fun BookContext.toTransportCreate() = BookCreateResponse(
    responseType = "create",
    result = if (state == BookState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    book = bookResponse.toTransportBook()
)

fun BookContext.toTransportRead() = BookReadResponse(
    responseType = "read",
    result = if (state == BookState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    book = bookResponse.toTransportBook()
)

fun BookContext.toTransportUpdate() = BookUpdateResponse(
    responseType = "update",
    result = if (state == BookState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    book = bookResponse.toTransportBook()
)

fun BookContext.toTransportDelete() = BookDeleteResponse(
    responseType = "delete",
    result = if (state == BookState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    book = bookResponse.toTransportBook()
)

fun BookContext.toTransportSearch() = BookSearchResponse(
    responseType = "search",
    result = if (state == BookState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    books = booksResponse.toTransportBooks()
)

private fun List<Book>.toTransportBooks(): List<BookResponseObject>? = this
    .map { it.toTransportBook() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Book.toTransportBook(): BookResponseObject = BookResponseObject(
    id = id.asString().takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    author = author.takeIf { it.isNotBlank() },
    status = status.toTransportBook(),
    rating = rating.takeIf { it.isNotBlank() },
    reviewText = reviewText.takeIf { it.isNotBlank() },
    userId = userId.asString().takeIf { it.isNotBlank() },
    lock = lock.asString().takeIf { it.isNotBlank() },
    permissions = permissionsClient.toTransportPermissions()
)

private fun Set<BookPermissionClient>.toTransportPermissions(): Set<BookPermissions>? = this
    .map { it.toTransportPermission() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun BookPermissionClient.toTransportPermission() = when (this) {
    BookPermissionClient.READ -> BookPermissions.READ
    BookPermissionClient.UPDATE -> BookPermissions.UPDATE
    BookPermissionClient.DELETE -> BookPermissions.DELETE
    BookPermissionClient.MAKE_VISIBLE_OWN -> BookPermissions.MAKE_VISIBLE_OWN
    BookPermissionClient.MAKE_VISIBLE_GROUP -> BookPermissions.MAKE_VISIBLE_GROUP
    BookPermissionClient.MAKE_VISIBLE_PUBLIC -> BookPermissions.MAKE_VISIBLE_PUBLIC
}

private fun List<BookError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun BookError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

fun ru.otus.otuskotlin.bookshelf.common.models.BookStatus.toTransportBook(): BookStatus? = when (this) {
    ru.otus.otuskotlin.bookshelf.common.models.BookStatus.WANT_TO_READ -> BookStatus.WANT_TO_READ
    ru.otus.otuskotlin.bookshelf.common.models.BookStatus.READING -> BookStatus.READING
    ru.otus.otuskotlin.bookshelf.common.models.BookStatus.READ -> BookStatus.READ
    ru.otus.otuskotlin.bookshelf.common.models.BookStatus.NONE -> null
}
