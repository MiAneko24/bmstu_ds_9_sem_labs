package com.mianeko.paymentservice.api

import com.mianeko.paymentservice.api.models.Payment
import com.mianeko.paymentservice.api.models.PaymentTemplate
import com.mianeko.paymentservice.data.PaymentRepository
import com.mianeko.paymentservice.data.exceptions.PaymentCreationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/payment")
class PaymentApiHandler (
    private val paymentRepository: PaymentRepository
){
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
            return paymentRepository.create(template)
        } catch (e: PaymentCreationException) {
            throw PaymentCreationApiException()
        }
    }
}
