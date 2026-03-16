package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.bookshelf.common.models.Book
import ru.otus.otuskotlin.bookshelf.common.models.BookId
import ru.otus.otuskotlin.bookshelf.common.models.BookLock
import ru.otus.otuskotlin.bookshelf.common.models.BookPermissionClient
import ru.otus.otuskotlin.bookshelf.common.models.BookStatus
import ru.otus.otuskotlin.bookshelf.common.models.UserId


object BookshelfBookStub {
    fun get(): Book = BOOK_1.copy()

    fun prepareSearchList(): List<Book> = listOf(
        BOOK_1.copy(),
        BOOK_2.copy()
    )

    val BOOK_1: Book
        get() = Book(
            id = BookId("123"),
            title = "Тестовая книга",
            lock = BookLock("123"),
            author = "Тестовый автор",
            userId = UserId("Тесте"),
            status = BookStatus.READ,
            rating = "5",
            reviewText = "Отлично",
            permissionsClient = mutableSetOf(
                BookPermissionClient.READ,
                BookPermissionClient.UPDATE,
                BookPermissionClient.DELETE,
                BookPermissionClient.MAKE_VISIBLE_PUBLIC,
                BookPermissionClient.MAKE_VISIBLE_GROUP,
                BookPermissionClient.MAKE_VISIBLE_OWN,
            )
        )

    val BOOK_2: Book
        get() = Book(
            id = BookId("456"),
            title = "Тестовая книга #2",
            lock = BookLock("456"),
            author = "Тестовый автор #2",
            userId = UserId("Тест #2"),
            status = BookStatus.WANT_TO_READ,
            permissionsClient = mutableSetOf(
                BookPermissionClient.READ,
                BookPermissionClient.UPDATE,
                BookPermissionClient.DELETE,
                BookPermissionClient.MAKE_VISIBLE_PUBLIC,
                BookPermissionClient.MAKE_VISIBLE_GROUP,
                BookPermissionClient.MAKE_VISIBLE_OWN,
            )
        )
}
