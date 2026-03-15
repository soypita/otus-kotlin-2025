package http.v1

import BookshelfAppSettings
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import ru.otus.otuskotlin.bookshelf.api.v1.models.IRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.IResponse
import ru.otus.otuskotlin.bookshelf.mappers.v1.fromTransport
import ru.otus.otuskotlin.bookshelf.mappers.v1.toTransportBook
import ru.otus.otuskotlin.marketplace.controllerHelper
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: BookshelfAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    {
        fromTransport(receive<Q>())
    },
    { respond(toTransportBook()) },
    clazz,
    logId,
)
