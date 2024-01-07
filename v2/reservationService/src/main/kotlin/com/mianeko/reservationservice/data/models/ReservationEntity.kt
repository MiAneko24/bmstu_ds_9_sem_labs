package com.mianeko.reservationservice.data.models

import com.mianeko.common.reservation.ReservationStatus
import org.ktorm.entity.Entity
import java.time.LocalDate
import java.util.*

interface ReservationEntity : Entity<ReservationEntity> {
    companion object : Entity.Factory<ReservationEntity>()

    var id: Int
    var reservationUid: UUID
    var username: String
    var paymentUid: UUID
    var hotel: HotelEntity
    var status: ReservationStatus
    var startDate: LocalDate
    var endDate: LocalDate
}
