package com.mianeko.gateway.rabbitMq.models

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = RabbitMqRequest.LoyaltyDecrementReservationsRequest::class, name = "LOYALTY_DECREMENT_RESERVATIONS"),
)
sealed class RabbitMqRequest(
    val type: RequestType
) {
    enum class RequestType {
        LOYALTY_DECREMENT_RESERVATIONS,
    }

    data class LoyaltyDecrementReservationsRequest(
        val username: String,
    ) : RabbitMqRequest(RequestType.LOYALTY_DECREMENT_RESERVATIONS)
}