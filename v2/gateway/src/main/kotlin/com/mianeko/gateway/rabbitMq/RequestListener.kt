package com.mianeko.gateway.rabbitMq

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mianeko.common.exceptions.ServiceNotAvailableApiException
import com.mianeko.gateway.api.clients.LoyaltyClient
import com.mianeko.gateway.rabbitMq.models.RabbitMqRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RequestListener(
    private val objectMapper: ObjectMapper,
    private val loyaltyClient: LoyaltyClient,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private fun processMessage(message: Message) {
        when (val request = objectMapper.readValue<RabbitMqRequest>(message.body)) {
            is RabbitMqRequest.LoyaltyDecrementReservationsRequest -> {
                log.info("Got message $request")
                loyaltyClient.decrementReservationsWithRetry(
                    request.username
                ) {
                    log.warn("Couldn't process retry to loyalty service, waiting 10s, won't ack message")
                    Thread.sleep(RETRY_TIMEOUT_MS)
                    throw ServiceNotAvailableApiException("Can't process retry request to LoyaltyService")
                }
            }
        }
    }

    @Bean
    fun requestsListenerContainer(
        connectionFactory: ConnectionFactory,
        requestsQueue: Queue,
    ): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer(connectionFactory)
        container.addQueues(requestsQueue)
        container.setMessageListener {
            processMessage(it)
        }
        return container
    }

    companion object {
        private const val RETRY_TIMEOUT_MS = 10000L
    }
}