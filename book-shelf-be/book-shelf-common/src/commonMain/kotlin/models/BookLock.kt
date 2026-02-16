package ru.otus.otuskotlin.bookshelf.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class BookLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = BookLock("")
    }
}
