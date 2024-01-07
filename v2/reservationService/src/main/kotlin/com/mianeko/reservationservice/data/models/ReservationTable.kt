package com.mianeko.reservationservice.data.models

import com.mianeko.common.reservation.ReservationStatus
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

object ReservationTable: Table<ReservationEntity>("reservation", schema = "reservations")  {
    val id = int("id").bindTo { it.id }
    val reservationUid = uuid("reservation_uid").bindTo { it.reservationUid }
    val username = varchar("username").bindTo { it.username }
    val paymentUid = uuid("payment_uid").bindTo { it.paymentUid }
    val hotelId = int("hotel_id").references(HotelTable) { it.hotel }
    val status = enum<ReservationStatus>("status").bindTo { it.status }
    val startDate = date("start_date").bindTo { it.startDate }
    val endDate = date("end_date").bindTo { it.endDate }
}

val Database.reservations get() = this.sequenceOf(ReservationTable)
