package com.mianeko.reservationservice.api.models

import com.mianeko.reservationservice.data.models.ReservationStatus
import java.time.Instant
import java.util.UUID

data class ReservationInfo(
    val reservationUid: UUID,
    val hotel: ShortHotelInfo,
    val startDate: Instant,
    val endDate: Instant,
    val paymentUid: UUID,
    val status: ReservationStatus
)

data class ReservationTemplate(
    val hotelUid: UUID,
    val username: String,
    val startDate: Instant,
    val endDate: Instant,
    val paymentUid: UUID,
)
