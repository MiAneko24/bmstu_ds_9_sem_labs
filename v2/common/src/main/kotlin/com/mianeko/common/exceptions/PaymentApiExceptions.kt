package com.mianeko.common.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*


open class PaymentApiException(
    override val message: String?
): RuntimeException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Payment was not found")
class PaymentNotFound(
    private val uuid: UUID,
    override val message: String? = "Could not find payment with id $uuid"
) : PaymentApiException(message)

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
class PaymentCreationApiException(
    override val message: String? = "Database was unable to save entity"
) : PaymentApiException(message)
