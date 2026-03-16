package kafka

import ru.otus.otuskotlin.bookshelf.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.bookshelf.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.bookshelf.api.v1.models.IRequest
import ru.otus.otuskotlin.bookshelf.api.v1.models.IResponse
import ru.otus.otuskotlin.bookshelf.common.BookContext
import ru.otus.otuskotlin.bookshelf.mappers.v1.fromTransport
import ru.otus.otuskotlin.bookshelf.mappers.v1.toTransportBook

class ConsumerStrategyV1 : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: BookContext): String {
        val response: IResponse = source.toTransportBook()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: BookContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
