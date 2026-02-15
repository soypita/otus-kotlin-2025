package ru.otus.otuskotlin.bookshelf.common.exceptions

import ru.otus.otuskotlin.bookshelf.common.models.BookCommand


class UnknownBookCommand(command: BookCommand) : Throwable("Wrong command $command at mapping toTransport stage")
