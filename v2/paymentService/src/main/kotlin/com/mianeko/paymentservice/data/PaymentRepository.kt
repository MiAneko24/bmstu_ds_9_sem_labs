package com.mianeko.paymentservice.data

import com.mianeko.paymentservice.api.models.Payment
import com.mianeko.paymentservice.api.models.PaymentTemplate
import com.mianeko.paymentservice.data.exceptions.PaymentCreationException
import com.mianeko.paymentservice.data.models.PaymentStatus
import com.mianeko.paymentservice.data.models.PaymentsEntity
import com.mianeko.paymentservice.data.models.payments
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.toList
import org.springframework.stereotype.Repository
import java.util.UUID

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
                    status = PaymentStatus.CANCELLED
                    flushChanges()
                }
            }
    }

    override fun create(paymentTemplate: PaymentTemplate): Payment {
        val uuid = UUID.randomUUID()
        val paymentEntity = PaymentsEntity {
            this.paymentUid = uuid
            this.price = paymentTemplate.price
            this.status = PaymentStatus.PAID
        }
        db.payments.add(paymentEntity)
        return get(uuid) ?: throw PaymentCreationException()
    }
}
