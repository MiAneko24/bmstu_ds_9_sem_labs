package com.mianeko.gateway.rabbitMq

import com.fasterxml.jackson.databind.ObjectMapper
import com.mianeko.gateway.rabbitMq.models.RabbitMqRequest
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
    fun sendRequest(request: RabbitMqRequest) {
        val message = Message(
            objectMapper.writeValueAsBytes(request)
        )
        rabbitTemplate.send(requestsExchange.name, message)
    }
}