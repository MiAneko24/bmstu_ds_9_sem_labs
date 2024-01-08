package com.mianeko.common.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


open class LoyaltyApiException(
    override val message: String?
): RuntimeException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Reservation was not found")
class ReservationsNotFound(
    private val username: String,
    override val message: String? = "Could not find reservations for user $username"
) : LoyaltyApiException(message)

