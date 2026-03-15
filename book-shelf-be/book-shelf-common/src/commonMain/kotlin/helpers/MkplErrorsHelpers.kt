import ru.otus.otuskotlin.bookshelf.common.models.BookError


fun Throwable.asBookError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = BookError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
