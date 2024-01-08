package com.mianeko.gateway.api.clients

import com.mianeko.common.payment.Payment
import com.mianeko.common.payment.PaymentTemplate
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(value = "paymentService", url = "\${custom.services.payment}")
interface PaymentClient {
    @GetMapping
    fun getPayments(): List<Payment>

    @GetMapping("/{uuid}")
    fun getPaymentById(
        @PathVariable uuid: UUID
    ): Payment

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(
        @PathVariable uuid: UUID
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody template: PaymentTemplate
    ): Payment
}
