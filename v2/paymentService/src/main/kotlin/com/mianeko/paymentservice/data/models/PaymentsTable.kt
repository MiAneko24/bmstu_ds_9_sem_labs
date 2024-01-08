package com.mianeko.paymentservice.data.models

import com.mianeko.common.payment.PaymentStatus
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.uuid

object PaymentsTable : Table<PaymentsEntity>("payment", schema = "payments") {
    val id = int("id").bindTo { it.id }
    val paymentUid = uuid("payment_uid").bindTo { it.paymentUid }
    val status = enum<PaymentStatus>("status").bindTo { it.status }
    val price = int("price").bindTo { it.price }
}

val Database.payments get() = this.sequenceOf(PaymentsTable)
