package com.mianeko.reservationservice.data.models

import org.ktorm.entity.Entity
import java.time.Instant
import java.util.UUID

interface ReservationEntity : Entity<ReservationEntity> {
    companion object : Entity.Factory<ReservationEntity>()

    var id: Int
    var reservationUid: UUID
    var username: String
    var paymentUid: UUID
    var hotel: HotelEntity
    var status: ReservationStatus
    var startDate: Instant
    var endDate: Instant
}
