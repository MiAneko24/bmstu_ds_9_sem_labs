package com.mianeko.loyaltyservice.api

import com.mianeko.loyaltyservice.api.models.Loyalty
import com.mianeko.loyaltyservice.data.LoyaltyRepository
import com.mianeko.loyaltyservice.data.exceptions.IncorrectLoyaltyDecrement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/loyalty")
class LoyaltyApiHandler(
    private val loyaltyRepository: LoyaltyRepository
) {
    @GetMapping
    fun getLoyalties(): List<Loyalty> {
        return loyaltyRepository.getAll()
    }

    @GetMapping("/user_info/{username}")
    fun getLoyaltyForUser(
        @PathVariable username: String
    ): Loyalty {
        return loyaltyRepository.getForUser(username)
    }

    @PatchMapping("/actions/increment_reservations_count/{username}")
    fun incrementReservations(
        @PathVariable username: String
    ): Loyalty {
        val loyalty = loyaltyRepository.updateReservations(username, true)
        return loyalty
    }

    @PatchMapping("/actions/decrement_reservations_count/{username}")
    fun decrementReservations(
        @PathVariable username: String
    ): Loyalty {
        try {
            val loyalty = loyaltyRepository.updateReservations(username, false)
            return loyalty
        } catch (e: IncorrectLoyaltyDecrement) {
            throw ReservationsNotFound(username)
        }
    }
}
