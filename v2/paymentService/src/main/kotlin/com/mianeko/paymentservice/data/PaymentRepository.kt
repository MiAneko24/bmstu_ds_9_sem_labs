package com.mianeko.paymentservice.data

import com.mianeko.common.payment.Payment
import com.mianeko.common.payment.PaymentStatus
import com.mianeko.common.payment.PaymentTemplate
import com.mianeko.paymentservice.data.exceptions.PaymentCreationException
import com.mianeko.paymentservice.data.models.PaymentsEntity
import com.mianeko.paymentservice.data.models.payments
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.toList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*

interface PaymentRepository {
    fun getAll(): List<Payment>
    fun get(uuid: UUID): Payment?
    fun delete(uuid: UUID)
    fun create(paymentTemplate: PaymentTemplate): Payment
}

@Repository
class PaymentRepositoryImpl(
    private val db: Database
): PaymentRepository {
    val log: Logger = LoggerFactory.getLogger(this::class.java)
    private fun mapper(entity: PaymentsEntity): Payment {
        return Payment(entity.id, entity.paymentUid, entity.status, entity.price)
    }
    override fun getAll(): List<Payment> {
        return db.payments.toList().map(::mapper)
    }

    override fun get(uuid: UUID): Payment? {
        return db.payments.find { it.paymentUid eq uuid }?.let { mapper(it) }
    }

    override fun delete(uuid: UUID) {
        db.payments
            .find { it.paymentUid eq uuid }
            ?.let {
                it.apply {
                    status = PaymentStatus.CANCELED
                    flushChanges()
                }
            }
    }

    override fun create(paymentTemplate: PaymentTemplate): Payment {
        val uuid = UUID.randomUUID()
        log.info("template is ${paymentTemplate.price}")
        val paymentEntity = PaymentsEntity {
            this.paymentUid = uuid
            this.price = paymentTemplate.price
            this.status = PaymentStatus.PAID
        }
        log.info("Generated payment entity is $paymentEntity")
        db.payments.add(paymentEntity)
        return get(uuid) ?: throw PaymentCreationException()
    }
}
