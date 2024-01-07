package com.mianeko.reservationservice.api

import com.mianeko.reservationservice.api.models.Hotel
import com.mianeko.reservationservice.api.models.ReservationInfo
import com.mianeko.reservationservice.api.models.ReservationTemplate
import com.mianeko.reservationservice.data.HotelNotExistException
import com.mianeko.reservationservice.data.ReservationCreationException
import com.mianeko.reservationservice.data.ReservationRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/reservation_service/")
class ReservationApiHandler(
    private val reservationRepository: ReservationRepository
) {

    @GetMapping("/hotels/")
    fun getAllHotels(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): List<Hotel> {
        return reservationRepository.getAllHotels(page, size)
    }

    @GetMapping("/reservations/user_info/{username}")
    fun getInfoByUserId(
        @PathVariable username: String
    ): List<ReservationInfo> {
        return reservationRepository.getReservationInfoForUser(username)
    }

    @GetMapping("/hotels/{uuid}")
    fun getHotelPrice(
        @PathVariable uuid: UUID
    ): Int {
        try {
            return reservationRepository.getPriceByHotelId(uuid)
        } catch (e: HotelNotExistException) {
            throw HotelNotFoundApiException(e.message)
        }
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReservation(
        @RequestBody reservationTemplate: ReservationTemplate
    ): ReservationInfo {
        try {
            return reservationRepository.createReservation(reservationTemplate)
        } catch (e: ReservationCreationException) {
            throw ReservationCreationApiException()
        }
    }

    @GetMapping("/reservations/{uuid}")
    fun getReservationInfo(
        @PathVariable uuid: UUID
    ): ReservationInfo {
        return reservationRepository.getReservationInfoById(uuid) ?: throw ReservationNotFoundApiException(uuid)
    }

    @DeleteMapping("/reservations/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReservation(
        @PathVariable uuid: UUID
    ) {
        reservationRepository.deleteReservation(uuid)
    }
}
