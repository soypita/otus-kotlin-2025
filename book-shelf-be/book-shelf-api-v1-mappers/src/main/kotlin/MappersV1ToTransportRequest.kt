package ru.otus.otuskotlin.bookshelf.mappers.v1

import ru.otus.otuskotlin.bookshelf.api.v1.models.*
import ru.otus.otuskotlin.bookshelf.common.models.Book
import ru.otus.otuskotlin.bookshelf.common.models.BookFilter

fun Book.toTransportCreate() = BookCreateObject(
    title = this.title.takeIf { it.isNotBlank() },
    author = this.author.takeIf { it.isNotBlank() },
    status = this.status.toTransportBook(),
    rating = this.rating.takeIf { it.isNotBlank() },
    reviewText = this.reviewText.takeIf { it.isNotBlank() },
)

fun Book.toTransportUpdate() = BookUpdateObject(
    id = this.id.asString().takeIf { it.isNotBlank() },
    title = this.title.takeIf { it.isNotBlank() },
    author = this.author.takeIf { it.isNotBlank() },
    status = this.status.toTransportBook(),
    rating = this.rating.takeIf { it.isNotBlank() },
    reviewText = this.reviewText.takeIf { it.isNotBlank() },
    lock = lock.asString().takeIf { it.isNotBlank() },
)

fun Book.toTransportRead() = BookReadObject(
    id = this.id.asString().takeIf { it.isNotBlank() },
)

fun Book.toTransportDelete() = BookDeleteObject(
    id = this.id.asString().takeIf { it.isNotBlank() },
    lock = lock.asString().takeIf { it.isNotBlank() },
)

fun BookFilter.toTransportSearch() = BookSearchFilter(
    title = this.title.takeIf { it.isNotBlank() },
    author = this.author.takeIf { it.isNotBlank() },
    rating = this.rating.takeIf { it.isNotBlank() },
    status = this.status.toTransportBook(),
)
