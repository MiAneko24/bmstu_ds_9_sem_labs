package com.mianeko.gateway.api

import com.mianeko.gateway.api.clients.ReservationClient
import com.mianeko.gateway.api.models.HotelInfo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hotels")
class HotelApiHandler(
    private val reservationClient: ReservationClient
) {
    @GetMapping
    fun getHotels(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "1") size: Int
    ): HotelInfo {
        return reservationClient.getAllHotels(page, size).let {
            HotelInfo(
                page = page,
                pageSize = size,
                totalElements = it.size,
                items = it
            )
        }
    }
}
