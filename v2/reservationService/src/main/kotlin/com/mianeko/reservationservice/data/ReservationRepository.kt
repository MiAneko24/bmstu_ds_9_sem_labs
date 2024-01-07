package com.mianeko.reservationservice.data

import com.mianeko.reservationservice.api.models.Hotel
import com.mianeko.reservationservice.api.models.ShortHotelInfo
import com.mianeko.reservationservice.api.models.ReservationInfo
import com.mianeko.reservationservice.api.models.ReservationTemplate
import com.mianeko.reservationservice.data.models.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.springframework.stereotype.Repository
import java.util.UUID

interface ReservationRepository {
    fun getAllHotels(page: Int, size: Int): List<Hotel>
    fun getReservationInfoForUser(username: String): List<ReservationInfo>
    fun getReservationInfoById(uuid: UUID): ReservationInfo?
    fun getPriceByHotelId(uuid: UUID): Int
    fun createReservation(reservationTemplate: ReservationTemplate): ReservationInfo
    fun deleteReservation(reservationUid: UUID)
}

@Repository
class ReservationRepositoryImpl(
    private val db: Database
): ReservationRepository {
    private fun mapper(hotelEntity: HotelEntity): ShortHotelInfo {
        return ShortHotelInfo(
            hotelUid = hotelEntity.hotelUid,
            name = hotelEntity.name,
            fullAddress = "${hotelEntity.country}, ${hotelEntity.city}, ${hotelEntity.address}",
            stars = hotelEntity.stars,
        )
    }

    private fun mapper(reservationEntity: ReservationEntity): ReservationInfo {
        return ReservationInfo(
            reservationUid = reservationEntity.reservationUid,
            hotel = mapper(reservationEntity.hotel),
            startDate = reservationEntity.startDate,
            endDate = reservationEntity.endDate,
            paymentUid = reservationEntity.paymentUid,
            status = reservationEntity.status
        )
    }

    override fun getAllHotels(page: Int, size: Int): List<Hotel> {
        return db.from(HotelTable).select().limit(page * size - 1, size).mapNotNull {
            val hotelUid = it[HotelTable.hotelUid]
            val name = it[HotelTable.name]
            val country = it[HotelTable.country]
            val city = it[HotelTable.city]
            val address = it[HotelTable.address]
            val stars = it[HotelTable.stars]
            val price = it[HotelTable.price]
            Hotel(
                hotelUid = hotelUid!!,
                name = name ?: "",
                country = country ?: "",
                city = city ?: "",
                address = address ?: "",
                stars = stars,
                price = price ?: 0
            )
        }
    }

    override fun getReservationInfoForUser(username: String): List<ReservationInfo> {
        return db.reservations.filter { it.username eq username }.map(::mapper)
    }

    override fun getReservationInfoById(uuid: UUID): ReservationInfo? {
        return db.reservations.find { it.reservationUid eq uuid }?.let { mapper(it) }
    }

    override fun getPriceByHotelId(uuid: UUID): Int {
        return db.hotels.find { it.hotelUid eq uuid }?.price ?: throw HotelNotExistException(uuid)
    }

    override fun createReservation(reservationTemplate: ReservationTemplate): ReservationInfo {
        val reservationUid = UUID.randomUUID()
        val hotel = db.hotels.find { it.hotelUid eq reservationTemplate.hotelUid } ?: throw HotelNotExistException(reservationTemplate.hotelUid)
        val reservation = ReservationEntity {
            this.reservationUid = reservationUid
            this.username = reservationTemplate.username
            this.paymentUid = reservationTemplate.paymentUid
            this.hotel = hotel
            this.status = ReservationStatus.PAID
            this.startDate = reservationTemplate.startDate
            this.endDate = reservationTemplate.endDate
        }
        db.reservations.add(reservation)
        return db
            .reservations
            .find { it.reservationUid eq reservationUid }
            ?.let { mapper(it) }
        ?: throw ReservationCreationException()
    }

    override fun deleteReservation(reservationUid: UUID) {
        db.reservations
            .find { it.reservationUid eq reservationUid }
            ?.let {
                it.apply {
                    status = ReservationStatus.CANCELLED
                    flushChanges()
                }
            }
    }

}
