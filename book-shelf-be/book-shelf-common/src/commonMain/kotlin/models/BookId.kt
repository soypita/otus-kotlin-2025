package ru.otus.otuskotlin.bookshelf.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class BookId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = BookId("")
    }
}
