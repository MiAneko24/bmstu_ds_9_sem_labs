package com.mianeko.gateway.api.models

data class UserInfo(
    val reservations: List<PaidReservationInfo>,
    val loyalty: ShortLoyaltyInfo
)
