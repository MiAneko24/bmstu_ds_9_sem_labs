package com.mianeko.gateway.api.clients

import com.mianeko.common.loyalty.Loyalty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(value = "loyaltyService", url = "\${custom.services.loyalty}")
interface LoyaltyClient {
    @GetMapping
    fun getLoyalties(): List<Loyalty>

    @GetMapping("/user_info/{username}")
    fun getLoyaltyForUser(
        @PathVariable username: String
    ): Loyalty

    @PostMapping("/actions/increment_reservations_count/{username}")
    fun incrementReservations(
        @PathVariable username: String
    ): Loyalty

    @PostMapping("/actions/decrement_reservations_count/{username}")
    fun decrementReservations(
        @PathVariable username: String
    ): Loyalty
}
