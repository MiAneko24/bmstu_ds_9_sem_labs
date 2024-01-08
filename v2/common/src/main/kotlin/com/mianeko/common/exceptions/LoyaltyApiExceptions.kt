package com.mianeko.common.exceptions


open class LoyaltyApiException(
    override val message: String?
): RuntimeException(message)

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Reservation was not found")
class ReservationsNotFound(
    private val username: String,
    override val message: String? = "Could not find reservations for user $username"
) : LoyaltyApiException(message)
