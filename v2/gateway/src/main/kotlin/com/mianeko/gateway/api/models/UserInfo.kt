package com.mianeko.gateway.api.models

data class UserInfo(
    val reservations: List<BasePaidReservationInfo>,
    val loyalty: BaseShortLoyaltyInfo
)
