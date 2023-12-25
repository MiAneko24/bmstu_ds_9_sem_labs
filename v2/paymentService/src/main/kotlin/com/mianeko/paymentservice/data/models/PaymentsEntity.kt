package com.mianeko.paymentservice.data.models

import org.ktorm.entity.Entity
import java.util.UUID

interface PaymentsEntity : Entity<PaymentsEntity> {
    companion object : Entity.Factory<PaymentsEntity>()

    var id: Int
    var paymentUid: UUID
    var status: PaymentStatus
    var price: Int
}
