package ru.otus.otuskotlin.bookshelf.common.models

import ru.otus.otuskotlin.marketplace.logging.common.LogLevel

data class BookError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
