package com.mianeko.paymentservice.api

import com.mianeko.common.exceptions.PaymentCreationApiException
import com.mianeko.common.exceptions.PaymentNotFound
import com.mianeko.common.payment.Payment
import com.mianeko.common.payment.PaymentTemplate
import com.mianeko.paymentservice.data.PaymentRepository
import com.mianeko.paymentservice.data.exceptions.PaymentCreationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/payments")
class PaymentApiHandler (
    private val paymentRepository: PaymentRepository
){
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    @GetMapping
    fun getPayments(): List<Payment> {
        return paymentRepository.getAll()
    }

    @GetMapping("/{uuid}")
    fun getPaymentById(
        @PathVariable uuid: UUID
    ): Payment {
        return paymentRepository.get(uuid) ?: throw PaymentNotFound(uuid)
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(
        @PathVariable uuid: UUID
    ) {
        paymentRepository.delete(uuid)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody template: PaymentTemplate
    ): Payment {
        try {
            log.info("Got request $template")
            return paymentRepository.create(template)
        } catch (e: PaymentCreationException) {
            throw PaymentCreationApiException()
        }
    }
}
