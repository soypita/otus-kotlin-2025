package kafka

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.log
import kotlinx.coroutines.launch

fun Application.kafkaModule() {
    log.info("Preparing kafkaModule")

    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()))
    monitor.subscribe(ApplicationStopping) {
        log.info("Closing kafka consumer")

        consumer.close()
    }
    launch {
        consumer.startSusp()
    }
}
