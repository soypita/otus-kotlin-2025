package http.plugins

import BookshelfAppSettings
import BookshelfBookProcessor
import io.ktor.server.application.*
import ru.otus.otuskotlin.bookshelf.common.BookShelfCorSettings

fun Application.initAppSettings(): BookshelfAppSettings {
    val corSettings = BookShelfCorSettings(
        loggerProvider = getLoggerProviderConf(),
    )
    return BookshelfAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = BookshelfBookProcessor(corSettings),
    )
}
