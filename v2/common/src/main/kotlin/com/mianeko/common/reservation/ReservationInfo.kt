package com.mianeko.common.reservation

import java.time.LocalDate
import java.util.*

data class ReservationInfo(
    val reservationUid: UUID,
    val hotel: ShortHotelInfo,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val paymentUid: UUID,
    val status: ReservationStatus
)

data class ReservationTemplate(
    val hotelUid: UUID,
    val username: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val paymentUid: UUID,
)
