package ru.otus.otuskotlin.bookshelf.common

import ru.otus.otuskotlin.marketplace.logging.common.LoggerProvider

data class BookShelfCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
) {
    companion object {
        val NONE = BookShelfCorSettings()
    }
}
