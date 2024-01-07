package com.mianeko.common.payment

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
