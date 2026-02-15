package ru.otus.otuskotlin.bookshelf.common.models

data class Book(
    var id: BookId = BookId.NONE,
    var title: String = "",
    var author: String = "",
    var userId: UserId = UserId.NONE,
    var status: BookStatus = BookStatus.NONE,
    var rating: String = "",
    var reviewText: String = "",
    var lock: BookLock = BookLock.NONE,
    val permissionsClient: MutableSet<BookPermissionClient> = mutableSetOf(),
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Book()
    }
}
