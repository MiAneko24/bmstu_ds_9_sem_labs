package com.mianeko.loyaltyservice.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


open class LoyaltyApiException(
    override val message: String?
): RuntimeException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Loyalty was not found")
class LoyaltyNotFoundException(
    override val message: String?
) : LoyaltyApiException(message)

