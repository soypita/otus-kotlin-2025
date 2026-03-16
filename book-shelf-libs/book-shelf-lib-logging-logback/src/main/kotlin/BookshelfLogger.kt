import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.logging.common.ILogWrapper
import kotlin.jvm.java
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun bookshelfLoggerLogback(logger: Logger): ILogWrapper = BookshelfLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun bookshelfLoggerLogback(clazz: KClass<*>): ILogWrapper = bookshelfLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun bookshelfLoggerLogback(loggerId: String): ILogWrapper = bookshelfLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
