package com.mianeko.gateway.api.clients

import com.mianeko.common.reservation.Hotel
import com.mianeko.common.reservation.ReservationInfo
import com.mianeko.common.reservation.ReservationTemplate
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(value = "reservationService", url = "\${custom.services.reservation}")
interface ReservationClient {
    @GetMapping("/hotels/")
    fun getAllHotels(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): List<Hotel>

    @GetMapping("/reservations/user_info/{username}")
    fun getInfoByUserId(
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
