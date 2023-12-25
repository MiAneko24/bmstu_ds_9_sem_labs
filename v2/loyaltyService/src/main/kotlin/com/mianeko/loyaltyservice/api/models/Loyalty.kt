package com.mianeko.loyaltyservice.api.models

import com.mianeko.loyaltyservice.data.models.LoyaltyLevel

data class Loyalty(
    var id: Int,
    var username: String,
    var reservationCount: Int,
    var status: LoyaltyLevel,
    var discount: Int
)
