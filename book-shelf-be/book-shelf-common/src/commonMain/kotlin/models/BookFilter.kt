package ru.otus.otuskotlin.bookshelf.common.models

data class BookFilter(
    var title: String = "",
    var author: String = "",
    var rating: String = "",
    var status: BookStatus = BookStatus.NONE,
)
