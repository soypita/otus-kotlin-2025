package http.plugins

import bookshelfLoggerLogback
import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.logging.common.LoggerProvider

fun Application.getLoggerProviderConf(): LoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> LoggerProvider { bookshelfLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values is logback (default)")
    }

