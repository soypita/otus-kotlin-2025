package ru.otus.otuskotlin.marketplace

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.bookshelf.api.log1.models.BookFilterLog
import ru.otus.otuskotlin.bookshelf.api.log1.models.BookLog
import ru.otus.otuskotlin.bookshelf.api.log1.models.BookshelfLogModel
import ru.otus.otuskotlin.bookshelf.api.log1.models.CommonLogModel
import ru.otus.otuskotlin.bookshelf.api.log1.models.ErrorLogModel
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.models.Book
import ru.otus.otuskotlin.bookshelf.common.models.BookError
import ru.otus.otuskotlin.bookshelf.common.models.BookFilter
import ru.otus.otuskotlin.bookshelf.common.models.BookId
import ru.otus.otuskotlin.bookshelf.common.models.BookRequestId
import ru.otus.otuskotlin.bookshelf.common.models.BookStatus
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun BookContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ok-marketplace",
    book = toBookLog(),
    errors = errors.map { it.toLog() },
)

private fun BookContext.toBookLog(): BookshelfLogModel? {
    val bookNone = Book()
    return BookshelfLogModel(
        requestId = requestId.takeIf { it != BookRequestId.NONE }?.asString(),
        requestBook = bookRequest.takeIf { it != bookNone }?.toLog(),
        responseBook = bookResponse.takeIf { it != bookNone }?.toLog(),
        responseBooks = booksResponse.takeIf { it.isNotEmpty() }?.filter { it != bookNone }?.map { it.toLog() },
        requestFilter = bookFilterRequest.takeIf { it != BookFilter() }?.toLog(),
    ).takeIf { it != BookshelfLogModel() }
}

private fun BookFilter.toLog() = BookFilterLog(
    title = title.takeIf { it.isNotBlank() },
    author = author.takeIf { it.isNotBlank() },
    rating = rating.takeIf { it.isNotBlank() },
    status = status.takeIf { it != BookStatus.NONE }?.name,
)

private fun BookError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun Book.toLog() = BookLog(
    id = id.takeIf { it != BookId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    author = author.takeIf { it.isNotBlank() },
    status = status.takeIf { it != BookStatus.NONE }?.name,
    rating = rating.takeIf { it.isNotBlank() },
    reviewText = reviewText.takeIf { it.isNotBlank() },
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)
