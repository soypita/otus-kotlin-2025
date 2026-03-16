package kafka

import ru.otus.otuskotlin.bookshelf.common.BookContext

interface IConsumerStrategy {
    /**
     * Топики, для которых применяется стратегия
     */
    fun topics(config: AppKafkaConfig): InputOutputTopics
    /**
     * Сериализатор для версии API
     */
    fun serialize(source: BookContext): String
    /**
     * Десериализатор для версии API
     */
    fun deserialize(value: String, target: BookContext)
}
