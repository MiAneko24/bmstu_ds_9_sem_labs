package com.mianeko.gateway.rabbitMq

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.gateway.rabbitMq.models.RabbitMqRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RequestSender(
    private val rabbitTemplate: RabbitTemplate,
    private val objectMapper: ObjectMapper,
    private val requestsExchange: Exchange,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun sendRequest(request: RabbitMqRequest) {
        val message = Message(
            objectMapper.writeValueAsBytes(request)
        )
        log.info("Send message $message")
        rabbitTemplate.send(requestsExchange.name, message)
    }
}