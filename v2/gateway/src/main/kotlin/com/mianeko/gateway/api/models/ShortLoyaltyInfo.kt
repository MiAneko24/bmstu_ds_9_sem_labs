package com.mianeko.gateway.api.models

import com.mianeko.common.loyalty.LoyaltyLevel

data class ShortLoyaltyInfo(
    val status: LoyaltyLevel,
    val discount: Int
)

data class FullLoyaltyInfo(
    val status: LoyaltyLevel,
    val discount: Int,
    val reservationCount: Int
)
