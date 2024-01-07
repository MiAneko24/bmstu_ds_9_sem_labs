package com.mianeko.reservationservice.data

import java.util.UUID

open class ReservationException(
    override val message: String?
): IllegalStateException(message)

data class HotelNotExistException(
    val hotelUuid: UUID,
    override val message: String? = "Hotel with id $hotelUuid does not exist"
): ReservationException(message)

data class ReservationCreationException(
    override val message: String? = "Could not create reservation"
): ReservationException(message)
