package com.mianeko.common.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

open class ReservationApiException(
    override val message: String?
): RuntimeException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Hotel was not found")
class HotelNotFoundApiException(
    val id: UUID,
    override val message: String? = "Could not find hotel with id $id"
) : ReservationApiException(message)

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
class ReservationCreationApiException(
    override val message: String? = "Database was unable to save entity"
) : ReservationApiException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Reservation was not found")
class ReservationNotFoundApiException(
    val uuid: UUID,
    override val message: String? = "Reservation with id $uuid was not found"
) : ReservationApiException(message)
