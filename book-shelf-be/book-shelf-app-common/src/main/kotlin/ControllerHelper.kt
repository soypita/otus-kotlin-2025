package ru.otus.otuskotlin.marketplace

import asBookError
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.common.models.BookCommand
import ru.otus.otuskotlin.bookshelf.common.models.BookState
import kotlin.reflect.KClass

suspend inline fun <T> IBookShelfAppSettings.controllerHelper(
    crossinline getRequest: suspend BookContext.() -> Unit,
    crossinline toResponse: suspend BookContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = BookContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
            e = e,
        )
        ctx.state = BookState.FAILING
        ctx.errors.add(e.asBookError())
        processor.exec(ctx)
        if (ctx.command == BookCommand.NONE) {
            ctx.command = BookCommand.READ
        }
        ctx.toResponse()
    }
}
