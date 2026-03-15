package ru.otus.otuskotlin.marketplace

import BookshelfBookProcessor
import ru.otus.otuskotlin.bookshelf.common.BookShelfCorSettings


interface IBookShelfAppSettings {
    val processor: BookshelfBookProcessor
    val corSettings: BookShelfCorSettings
}
