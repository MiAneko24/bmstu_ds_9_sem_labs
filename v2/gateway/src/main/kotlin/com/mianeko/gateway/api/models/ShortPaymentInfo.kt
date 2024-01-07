package com.mianeko.gateway.api.models

import com.mianeko.common.payment.PaymentStatus

data class ShortPaymentInfo(
    val status: PaymentStatus,
    val price: Int
)
