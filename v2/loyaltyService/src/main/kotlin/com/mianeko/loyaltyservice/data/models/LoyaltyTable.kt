package com.mianeko.loyaltyservice.data.models

import com.mianeko.common.loyalty.LoyaltyLevel
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object LoyaltyTable: Table<LoyaltyEntity>("loyalty", schema = "loyalties") {
    val id = int("id").bindTo { it.id }
    val username = varchar("username").bindTo { it.username }
    val reservationCount = int("reservation_count").bindTo { it.reservationCount }
    val status = enum<LoyaltyLevel>("status").bindTo { it.status }
    val discount = int("discount").bindTo { it.discount }
}

val Database.loyalties get() = this.sequenceOf(LoyaltyTable)
