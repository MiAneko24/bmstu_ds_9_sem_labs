package com.mianeko.gateway.api.models

import com.mianeko.common.loyalty.LoyaltyLevel

sealed class BaseShortLoyaltyInfo

data class ShortLoyaltyInfo(
    val status: LoyaltyLevel,
    val discount: Int
): BaseShortLoyaltyInfo()

data object EmptyShortLoyaltyInfo: BaseShortLoyaltyInfo()

sealed class BaseFullLoyaltyInfo

data class FullLoyaltyInfo(
    val status: LoyaltyLevel,
    val discount: Int,
    val reservationCount: Int
): BaseFullLoyaltyInfo()

data object EmptyFullLoyaltyInfo: BaseFullLoyaltyInfo()
