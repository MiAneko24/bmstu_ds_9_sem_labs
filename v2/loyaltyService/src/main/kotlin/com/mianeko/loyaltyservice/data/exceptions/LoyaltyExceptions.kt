package com.mianeko.loyaltyservice.data.exceptions

open class LoyaltyException(
    override val message: String?
): IllegalStateException(message)

data class IncorrectLoyaltyDecrement(
    val username: String,
    override val message: String = "There are no reservations to cancel"
): LoyaltyException(message)

data class LoyaltyCreationException(
    override val message: String = "Could not create loyalty"
): LoyaltyException(message)
