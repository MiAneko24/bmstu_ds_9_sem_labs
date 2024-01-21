package com.mianeko.gateway.rabbitMq

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfiguration {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun connectionFactory(
        @Value("\${rabbitmq.host}") host: String,
        @Value("\${rabbitmq.port}") port: Int,
    ): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory(
            host,
            port
        )
        connectionFactory.rabbitConnectionFactory.username = "user"
        connectionFactory.rabbitConnectionFactory.password = "password"
        return connectionFactory
    }

    @Bean
    fun requestsContainerConnectionFactory(
        connectionFactory: ConnectionFactory,
    ): SimpleRabbitListenerContainerFactory {
        val containerFactory = SimpleRabbitListenerContainerFactory()
        containerFactory.setConnectionFactory(connectionFactory)
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL)
        return containerFactory
    }

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory,
    ): RabbitTemplate {
        return RabbitTemplate(connectionFactory)
    }

    @Bean
    fun requestsQueue(
        @Value("\${rabbitmq.queues.requests}") name: String,
    ): Queue {
        return Queue(name)
    }

    @Bean
    fun requestsExchange(
        @Value("\${rabbitmq.exchanges.requests}") name: String,
    ): Exchange {
        return DirectExchange(name)
    }
}