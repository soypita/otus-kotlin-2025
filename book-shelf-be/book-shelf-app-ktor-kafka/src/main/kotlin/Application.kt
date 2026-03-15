import io.ktor.server.application.Application
import io.ktor.server.cio.EngineMain
import http.restApiModule
import kafka.kafkaModule

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.modules(
) {
    restApiModule()
    kafkaModule()
}
