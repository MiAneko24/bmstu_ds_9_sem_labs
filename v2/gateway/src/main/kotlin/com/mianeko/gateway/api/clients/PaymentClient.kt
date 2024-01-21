package com.mianeko.gateway.api.clients

import com.mianeko.common.payment.Payment
import com.mianeko.common.payment.PaymentTemplate
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(
    value = "paymentService",
    url = "\${custom.services.payment}",
    fallback = PaymentClientFallback::class
)
@Primary
interface PaymentClient {
    @GetMapping("/{uuid}")
    fun getPaymentById(
        @PathVariable uuid: UUID
    ): Payment?

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

@Component
class PaymentClientFallback: PaymentClient {
    override fun getPaymentById(uuid: UUID): Payment? {
        return null
    }

    override fun deleteById(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun create(template: PaymentTemplate): Payment {
        TODO("Not yet implemented")
    }
}