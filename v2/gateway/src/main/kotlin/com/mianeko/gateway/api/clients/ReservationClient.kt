package com.mianeko.gateway.api.clients

import com.mianeko.common.exceptions.ServiceNotAvailableApiException
import com.mianeko.common.reservation.Hotel
import com.mianeko.common.reservation.ReservationInfo
import com.mianeko.common.reservation.ReservationTemplate
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(
    value = "reservationService",
    url = "\${custom.services.reservation}",
    fallback = ReservationClientFallback::class
)
@Primary
interface ReservationClient {
    @GetMapping("/hotels")
    fun getAllHotels(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): List<Hotel>

    @GetMapping("/reservations/user_info/{username}")
    fun getInfoByUserId(
        @PathVariable username: String
    ): List<ReservationInfo>

    @GetMapping("/reservations/user_info/{username}")
    fun getInfoByUserIdWithFallback(
        @PathVariable username: String
    ): List<ReservationInfo>

    @GetMapping("/hotels/{uuid}")
    fun getHotelPrice(
        @PathVariable uuid: UUID
    ): Int

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReservation(
        @RequestBody reservationTemplate: ReservationTemplate
    ): ReservationInfo

    @GetMapping("/reservations/{uuid}")
    fun getReservationInfo(
        @PathVariable uuid: UUID
    ): ReservationInfo

    @DeleteMapping("/reservations/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReservation(
        @PathVariable uuid: UUID
    )
}

@Component
class ReservationClientFallback: ReservationClient {
    override fun getAllHotels(page: Int, size: Int): List<Hotel> {
        throw ServiceNotAvailableApiException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun getInfoByUserId(username: String): List<ReservationInfo> {
        throw ServiceNotAvailableApiException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun getInfoByUserIdWithFallback(username: String): List<ReservationInfo> {
        return emptyList()
    }

    override fun getHotelPrice(uuid: UUID): Int {
        throw ServiceNotAvailableApiException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun createReservation(reservationTemplate: ReservationTemplate): ReservationInfo {
        TODO("Not yet implemented")
    }

    override fun getReservationInfo(uuid: UUID): ReservationInfo {
        throw ServiceNotAvailableApiException(SERVICE_NOT_AVAILABLE_MESSAGE)
    }

    override fun deleteReservation(uuid: UUID) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val SERVICE_NOT_AVAILABLE_MESSAGE = "Reservation Service unavailable"
    }
}