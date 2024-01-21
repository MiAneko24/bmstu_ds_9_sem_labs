package com.mianeko.gateway.api

import com.mianeko.gateway.api.clients.LoyaltyClient
import com.mianeko.gateway.api.models.BaseFullLoyaltyInfo
import com.mianeko.gateway.api.models.FullLoyaltyInfo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/loyalty")
class LoyaltyApiHandler(
    private val loyaltyClient: LoyaltyClient
) {
    @GetMapping
    fun getLoyaltyInfo(
        @RequestHeader("X-User-Name") username: String
    ): BaseFullLoyaltyInfo {
        val loyaltyInfo = loyaltyClient.getLoyaltyForUser(username)
        return FullLoyaltyInfo(
            status = loyaltyInfo.status,
            discount = loyaltyInfo.discount,
            reservationCount = loyaltyInfo.reservationCount
        )
    }
}
