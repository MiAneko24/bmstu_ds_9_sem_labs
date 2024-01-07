package com.mianeko.loyaltyservice.data.models

import com.mianeko.common.loyalty.LoyaltyLevel
import org.ktorm.entity.Entity

interface LoyaltyEntity: Entity<LoyaltyEntity> {
    companion object : Entity.Factory<LoyaltyEntity>()

    var id: Int
    var username: String
    var reservationCount: Int
    var status: LoyaltyLevel
    var discount: Int
}
