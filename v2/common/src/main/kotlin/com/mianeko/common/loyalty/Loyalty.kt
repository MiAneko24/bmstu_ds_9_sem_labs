package com.mianeko.common.loyalty

data class Loyalty(
    var id: Int,
    var username: String,
    var reservationCount: Int,
    var status: LoyaltyLevel,
    var discount: Int
)
