package com.mianeko.loyaltyservice.api

import com.mianeko.loyaltyservice.api.models.Loyalty
import com.mianeko.loyaltyservice.data.LoyaltyRepository
import org.springframework.web.bind.annotation.GetMapping
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
        val loyalty = loyaltyRepository.getForUser(username)

    }
}
