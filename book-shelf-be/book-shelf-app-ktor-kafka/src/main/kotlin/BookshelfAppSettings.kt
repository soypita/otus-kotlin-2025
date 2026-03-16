import ru.otus.otuskotlin.bookshelf.common.BookShelfCorSettings
import ru.otus.otuskotlin.marketplace.IBookShelfAppSettings

data class BookshelfAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: BookShelfCorSettings = BookShelfCorSettings(),
    override val processor: BookshelfBookProcessor = BookshelfBookProcessor(corSettings)
) : IBookShelfAppSettings
