package com.mianeko.reservationservice.data.models

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

object HotelTable: Table<HotelEntity>("hotels", schema = "reservations") {
    val id = int("id").primaryKey().bindTo { it.id }
    val hotelUid = uuid("hotel_uid").bindTo { it.hotelUid }
    val name = varchar("name").bindTo { it.name }
    val country = varchar("country").bindTo { it.country }
    val city = varchar("city").bindTo { it.city }
    val address = varchar("address").bindTo { it.address }
    val stars = int("stars").bindTo { it.stars }
    val price = int("price").bindTo { it.price }
}

val Database.hotels get() = this.sequenceOf(HotelTable)
