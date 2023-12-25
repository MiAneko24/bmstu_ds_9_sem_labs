package com.mianeko.paymentservice.data.models

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

object PaymentsTable : Table<PaymentsEntity>("payment", schema = "payments") {
    val id = int("id").bindTo { it.id }
    val paymentUid = uuid("payment_uid").bindTo { it.paymentUid }
    val status = enum<PaymentStatus>("status").bindTo { it.status }
    val price = int("price")
}

val Database.payments get() = this.sequenceOf(PaymentsTable)
