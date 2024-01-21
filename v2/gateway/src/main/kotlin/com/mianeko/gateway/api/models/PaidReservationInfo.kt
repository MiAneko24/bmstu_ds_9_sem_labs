package com.mianeko.gateway.api.models

import com.mianeko.common.reservation.ReservationStatus
import com.mianeko.common.reservation.ShortHotelInfo
import java.time.LocalDate
import java.util.*

sealed class BasePaidReservationInfo

data class PaidReservationInfo(
    val reservationUid: UUID,
    val hotel: ShortHotelInfo,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: ReservationStatus,
    val payment: BaseShortPaymentInfo
): BasePaidReservationInfo()

data object EmptyPaidReservationInfo: BasePaidReservationInfo()

data class BookReservationInfo(
    val reservationUid: UUID,
    val hotelUid: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val discount: Int,
    val status: ReservationStatus,
    val payment: ShortPaymentInfo
)
