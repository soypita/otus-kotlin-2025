package ru.otus.otuskotlin.marketplace.app.ktor.kafka.kafka

import kafka.AppKafkaConfig
import kafka.AppKafkaConsumer
import kafka.ConsumerStrategyV1
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.bookshelf.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.bookshelf.api.v1.apiV1ResponseDeserialize
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateObject
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookCreateResponse
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookDebug
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugMode
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookRequestDebugStubs
import ru.otus.otuskotlin.bookshelf.api.v1.models.BookStatus
import java.util.*
import kotlin.collections.first
import kotlin.collections.set
import kotlin.test.assertEquals


class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        BookCreateRequest(
                            book = BookCreateObject(
                                title = "New book#1",
                                author = "New author",
                                status = BookStatus.WANT_TO_READ,
                            ),
                            debug = BookDebug(
                                mode = BookRequestDebugMode.STUB,
                                stub = BookRequestDebugStubs.SUCCESS,
                            ),
                        ),
                    )
                )
            )
            app.close()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()

        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.start()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<BookCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("123", result.book?.id)
    }

    companion object {
        const val PARTITION = 0
    }
}


