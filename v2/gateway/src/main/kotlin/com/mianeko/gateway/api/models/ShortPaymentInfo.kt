package com.mianeko.gateway.api.models

import com.mianeko.common.payment.PaymentStatus

sealed class BaseShortPaymentInfo

data class ShortPaymentInfo(
    val status: PaymentStatus,
    val price: Int
): BaseShortPaymentInfo()

data object EmptyShortPaymentInfo: BaseShortPaymentInfo()
