package com.mianeko.paymentservice.data.exceptions

open class PaymentException(
    override val message: String?
): IllegalStateException(message)

data class PaymentCreationException(
    override val message: String? = "An error occurred during payment creation"
): PaymentException(message)
