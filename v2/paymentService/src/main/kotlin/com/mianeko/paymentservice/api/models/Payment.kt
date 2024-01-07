package com.mianeko.paymentservice.api.models

import com.mianeko.paymentservice.data.models.PaymentStatus
import java.util.*

data class Payment(
    val id: Int,
    val paymentUid: UUID,
    val status: PaymentStatus,
    val price: Int
)

data class PaymentTemplate(
    val price: Int
)
